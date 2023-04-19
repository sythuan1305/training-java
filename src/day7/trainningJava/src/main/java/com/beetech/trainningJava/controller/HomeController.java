package com.beetech.trainningJava.controller;

import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.utils.Utils;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;

@Controller
public class HomeController {
    @Autowired
    private ICartProductService cartProductService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/")
    public RedirectView readCookie(@CookieValue(value = "cart", defaultValue = "defaultCookieValue") String cookieValue,
                                   HttpServletResponse response)
            throws ParseException {
        if (accountService.isLogin() && !cookieValue.equals("defaultCookieValue")) {
            cartProductService.savesWithAuthenticated(Utils.JsonParserListObjectWithEncodedURL(cookieValue));
            // Xóa cookie
            Utils.deleteCookie("cart", response);
        }
        return new RedirectView("/user/product/list");
    }

    @PostMapping("/")
    public RedirectView homeList(@RequestParam("cartProductId") Integer[] cartProductId) {
        Arrays.stream(cartProductId).forEach(System.out::println);
        return new RedirectView("/user/product/list");
    }

    @GetMapping("/testXSS")
    public ModelAndView testXSS(@RequestParam(value = "name", required = false) String name, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView("testXSS");
        modelAndView.addObject("name", name);
        return modelAndView;
    }

}
