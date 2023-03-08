package com.beetech.trainningJava.controller.mvc.admin;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;

@Controller("mvcAdminController")
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/upload")
    public ModelAndView uploadProduct() {
        return new ModelAndView("product/upload");
    }

    @PostMapping("/upload")
    public ModelAndView uploadProduct(@RequestParam("name") String name, @RequestParam("price") BigInteger price,
                                      @RequestParam("quantity") Integer quantity, @RequestParam("image_url") String image_url) {
        ModelAndView modelAndView = new ModelAndView("product/uploadSuccess");
        ProductEntity productEntity = productService.save(new ProductEntity(name, price, quantity, image_url));
        modelAndView.addObject("product", productEntity);
        return modelAndView;
    }
}
