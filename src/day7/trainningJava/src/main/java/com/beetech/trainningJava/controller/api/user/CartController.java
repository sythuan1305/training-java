package com.beetech.trainningJava.controller.api.user;

import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("apiCartController")
@RequestMapping("/api/user/cart")
public class CartController {
    @Autowired
    ICartProductService cartProductService;

    @Autowired
    IProductService productService;

    @PostMapping("/testCase1")
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCase1Post() {
        cartProductService.TestMinusQuantity();
        cartProductService.TestSave(false);
    }

    @GetMapping("/testCase1")
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCase1Get() {
        cartProductService.TestMinusQuantity();
        cartProductService.TestSave(false);
    }

    @PostMapping("/testCase2")
    @Transactional(propagation = Propagation.REQUIRED)
    public void testCase2() {
        cartProductService.TestSave(false);
        cartProductService.TestMinusQuantity();
        throw new RuntimeException("Roll back 2 transaction");
    }

    @GetMapping("/testException")
    public void testException() throws Exception {
        throw new Exception("Test exception");
    }

    @GetMapping("/testMinusQuantity")
    public void testMinusQuantity() {
        productService.TestMinusQuantity(10000);
    }
}
