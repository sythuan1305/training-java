package com.beetech.trainningJava.controller.api.admin;

import com.beetech.trainningJava.service.ICartProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("apiAdminProductController")
@RequestMapping("/api/admin/product")
public class ProductController {
    @Autowired
    ICartProductService cartProductService;
    @GetMapping("/testCsrf")
    public void testCsrf() {
        cartProductService.TestMinusQuantity();
    }
}
