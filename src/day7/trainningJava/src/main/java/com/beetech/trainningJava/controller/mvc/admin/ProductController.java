package com.beetech.trainningJava.controller.mvc.admin;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.service.IProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.math.BigInteger;

@Controller("mvcAdminController")
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/upload")
    public ModelAndView uploadProduct() {
        return new ModelAndView("product/pages/upload");
    }

    @PostMapping("/upload")
    public ModelAndView uploadProduct(@RequestParam("name") String name, @RequestParam("price") BigDecimal price,
                                      @RequestParam("quantity") Integer quantity, @RequestParam("image_url") String image_url) {
        ModelAndView modelAndView = new ModelAndView("product/pages/uploadSuccess");
        ProductEntity productEntity = productService.save(new ProductEntity(name, price, quantity, image_url));
        modelAndView.addObject("product", productEntity);
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView listProduct(@Nullable @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        ModelAndView modelAndView = new ModelAndView("product/pages/list");
        modelAndView.addObject("page", productService.findAll(page, 5, "name"));
        return modelAndView;
    }

    @GetMapping("/information")
    public ModelAndView GetInformation(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product/pages/information");
        modelAndView.addObject("product", productService.getOne(id));
        return modelAndView;
    }
}
