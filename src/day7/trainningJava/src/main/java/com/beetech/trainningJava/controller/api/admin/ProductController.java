package com.beetech.trainningJava.controller.api.admin;

import com.beetech.trainningJava.service.ICartProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller này dùng để xử lý các request liên quan đến sản phẩm với quyền admin
 */
@Controller("apiAdminProductController")
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {
    private final ICartProductService cartProductService;

    @GetMapping("/testCsrf")
    public void testCsrf() {
        cartProductService.TestMinusQuantity();
    }
}
