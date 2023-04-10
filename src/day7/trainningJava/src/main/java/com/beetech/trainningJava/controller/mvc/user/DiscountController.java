package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import com.beetech.trainningJava.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller("mvcUserDiscountController")
@RequestMapping("/user/discount")
public class DiscountController {
    @Autowired
    private ICartProductService cartProductService;

    @Autowired
    private IProductDiscountConditionService discountProductService;

    @Autowired
    private IAccountService accountService;

    @PostMapping("/list")
    public ModelAndView information(@CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue,
                                    @RequestParam("cartProductId") Integer[] cartProductId) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("discount/list1");
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService.getListCartProductInforByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            cartProductInforModels = cartProductService.getListCartProductInforWithParser(
                    Utils.JsonParserListObjectWithEncodedBase64(cookieValue));
        }
        cartProductInforModels = cartProductService.getListCartProductInforWithCartProductArray(cartProductInforModels, cartProductId);
        List<DiscountModel> discountModels = discountProductService.getDiscountModels(cartProductInforModels);
        modelAndView.addObject("discounts", discountModels);
        modelAndView.addObject("cartProductIds", Arrays.asList(cartProductId));
        return modelAndView;
    }

//    @PostMapping("/choose")
//    public RedirectView choose(@RequestParam("discountId") Integer discountId) {
//        RedirectView redirectView = new RedirectView("/user/order/");
//        redirectView.addStaticAttribute("discountId", discountId);
//        return redirectView;
//    }
}