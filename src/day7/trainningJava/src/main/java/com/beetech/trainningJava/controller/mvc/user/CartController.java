package com.beetech.trainningJava.controller.mvc.user;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller("mvcUserCartController")
@RequestMapping("/user/cart")
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
    public ModelAndView information(
            @Loggable @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue,
            @RequestParam(value = "discountId", required = false) Integer discountId) throws JsonProcessingException {
        System.out.println("cookieValue: " + cookieValue + "cookieValue.length(): " + cookieValue.length());
        ModelAndView modelAndView = new ModelAndView("cart/information");
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService
                    .getCartProductInforListByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
            modelAndView.addObject("cartProducts", Utils.JsonParserString(cartProductInforModels));
        } else {
            if (!Utils.DEFAULT_COOKIE_VALUE.equals(cookieValue)) {
                cartProductInforModels = cartProductService.getCartProductInforListByCartProductParserList(
                        Utils.JsonParserListObjectWithEncodedURL(cookieValue));
                modelAndView.addObject("cartProducts", Utils.JsonParserString(cartProductInforModels));
            } else {
                modelAndView.addObject("cartProducts", new ArrayList<CartProductEntity>());
            }
        }
        DiscountModel discountModel = productDiscountConditionService.getDiscountModelByCartProductInforList(discountId,
                cartProductInforModels);
        modelAndView.addObject("discount", discountModel == null ? null : Utils.JsonParserString(discountModel));
        return modelAndView;
    }

    @GetMapping("/information-cart")
    public ModelAndView informationCart(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue)
            throws JsonProcessingException {
        System.out.println("cookieValue: " + cookieValue + "cookieValue.length(): " + cookieValue.length());
        ModelAndView modelAndView = new ModelAndView("cart/information-cart");
        // for test
        cartProductService.findCartProductEntityByCartProductEntity(new CartProductEntity());
        // end for test
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService
                    .getCartProductInforListByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            if (!Utils.DEFAULT_COOKIE_VALUE.equals(cookieValue)) {
                cartProductInforModels = cartProductService.getCartProductInforListByCartProductParserList(
                        Utils.JsonParserListObjectWithEncodedURL(cookieValue));
            }
        }
        modelAndView.addObject("cartProducts", cartProductInforModels);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView add(@RequestParam("productId") Integer productId,
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        System.out.println("cookieValue: " + cookieValue + "cookieValue.length(): " + cookieValue.length());
        ModelAndView modelAndView = new ModelAndView("cart/add-product");
        modelAndView.addObject("product", productService.getProductInforModelById(productId));
        return modelAndView;
    }

    @PostMapping("/add")
    public RedirectView add(@RequestParam("cartProduct") String cartProduct) throws ParseException {
        if (!accountService.isLogin()) {
            return new RedirectView("/user/cart/information-cart");
        }
        System.out.println("cartProduct: " + cartProduct);
        cartProductService.saveCartProductEntityListWithAuthenticatedByCartProductParserList(
                (List<Map<String, Object>>) new JSONParser(cartProduct).parse());
        return new RedirectView("/user/cart/information-cart");
    }
}
