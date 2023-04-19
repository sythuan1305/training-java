package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.aspect.LoggingAspect;
import com.beetech.trainningJava.aspect.annotation.Loggable;
import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.service.*;
import com.beetech.trainningJava.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller("mvcUserCartController")
@RequestMapping("/user/cart")
//@PostAuthorize("hasPermission('ROLE_USER')")
@Loggable
public class CartController {

    @Autowired
    private ICartProductService cartProductService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IProductDiscountConditionService productDiscountConditionService;

    @GetMapping("/information")
    public ModelAndView information(@Loggable @CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue,
                                     @RequestParam(value = "discountId", required = false) Integer discountId) throws JsonProcessingException {
        System.out.println("cookieValue: " + cookieValue + "cookieValue.length(): " + cookieValue.length());
        ModelAndView modelAndView = new ModelAndView("cart/information");
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService.getListCartProductInforByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
            modelAndView.addObject("cartProducts", Utils.JsonParserString(cartProductInforModels));
        } else {
            if (!cookieValue.equals("defaultCookieValue")) {
                cartProductInforModels = cartProductService.getListCartProductInforWithParser(Utils.JsonParserListObjectWithEncodedURL(cookieValue));
                modelAndView.addObject("cartProducts", Utils.JsonParserString(cartProductInforModels));
            } else {
                modelAndView.addObject("cartProducts", new ArrayList<CartProductEntity>());
            }
        }
//        List<DiscountModel> discountModels = productDiscountConditionService.getDiscountModels(cartProductInforModels);
//        System.out.println("discountModels.size(): " + discountModels.size());
//        discountModels.forEach(discountModel -> {
//            System.out.println("Code: " + discountModel.getCode());
//            System.out.println("discountModel.getConditions().size() " + discountModel.getConditions().size());
//            System.out.println("discountModel.getDescription() " + discountModel.getDescription());
//            discountModel.getConditions().forEach((key, conditionModel) -> {
//                System.out.println("getConditionType: " + conditionModel.getConditionEntity().getConditionType());
//                System.out.println("conditionModel.getProduct().getName(): " + conditionModel.getProduct().getName());
//                System.out.println("conditionModel.isEnoughCondition() " + conditionModel.isEnoughCondition());
//                System.out.println("conditionModel.getDescription() " + conditionModel.getDescription());
//            });
//
//            System.out.println("discountModel.isAbleToUse() " + discountModel.isAbleToUse());
//        });
        DiscountModel discountModel = productDiscountConditionService.getDiscountModel(discountId, cartProductInforModels);
        modelAndView.addObject("discount", discountModel == null ? null : Utils.JsonParserString(discountModel));
        return modelAndView;
    }

//    @Loggable
    @GetMapping("/information-cart")
    public ModelAndView informationCart(@CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue) throws JsonProcessingException {
//        System.out.println("test: " + test);
        System.out.println("cookieValue: " + cookieValue + "cookieValue.length(): " + cookieValue.length());
        ModelAndView modelAndView = new ModelAndView("cart/information-cart");
        // for test
        cartProductService.findByCartProductEntity(new CartProductEntity());
        // end for test
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService.getListCartProductInforByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            if (!cookieValue.equals("defaultCookieValue")) {
                cartProductInforModels = cartProductService.getListCartProductInforWithParser(Utils.JsonParserListObjectWithEncodedURL(cookieValue));
            }
        }
        modelAndView.addObject("cartProducts", cartProductInforModels);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add(@RequestParam("productId") Integer productId,
                            @CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue) {
        System.out.println("cookieValue: " + cookieValue + "cookieValue.length(): " + cookieValue.length());
        ModelAndView modelAndView = new ModelAndView("cart/add-product");
        modelAndView.addObject("product", productService.getProductInforModel(productId));
        return modelAndView;
    }

    @PostMapping("/add")
    public RedirectView add(@RequestParam("cartProduct") String cartProduct) throws ParseException {
        if (!accountService.isLogin()) {
            return new RedirectView("/user/cart/information-cart");
        }
        System.out.println("cartProduct: " + cartProduct);
        cartProductService.savesWithAuthenticated((List<Map<String, Object>>) new JSONParser(cartProduct).parse());
        return new RedirectView("/user/cart/information-cart");
    }
}
