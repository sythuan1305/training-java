package com.beetech.trainningJava.controller;

import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.utils.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
    @Autowired
    private ICartProductService cartProductService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/")
    public RedirectView readCartCookie(@CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cartCookieValue,
                                   HttpServletResponse response) {
        if (accountService.isLogin() && !Utils.DEFAULT_COOKIE_VALUE.equals(cartCookieValue)) {
            cartProductService.saveCartProductEntityListWithAuthenticatedByCartProductParserList(Utils.JsonParserListObjectWithEncodedURL(cartCookieValue));
            // Xóa cookie
            Utils.deleteCookie("cart", response);
        }
        return new RedirectView("/user/product/list");
    }
}
