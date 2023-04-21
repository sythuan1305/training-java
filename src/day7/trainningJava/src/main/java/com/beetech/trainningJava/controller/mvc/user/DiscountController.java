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
    public ModelAndView information(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue,
            @RequestParam("cartProductId") Integer[] cartProductId) throws JsonProcessingException {
        ModelAndView modelAndView = new ModelAndView("discount/list1");
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService
                    .getCartProductInforListByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
        } else {
            cartProductInforModels = cartProductService.getCartProductInforListByCartProductParserList(
                    Utils.JsonParserListObjectWithEncodedURL(cookieValue));
        }
        cartProductInforModels = cartProductService.getCartProductInforListByCartProductModelListAndCartProductArray(
                cartProductInforModels, cartProductId);
        List<DiscountModel> discountModels = discountProductService
                .getDiscountModelListByCartProductInforModelList(cartProductInforModels);
        modelAndView.addObject("discounts", discountModels);
        modelAndView.addObject("cartProductIds", Arrays.asList(cartProductId));
        return modelAndView;
    }
}