package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.IProductImageUrlService;
import com.beetech.trainningJava.service.IProductService;
import com.beetech.trainningJava.service.IFileService;
import com.beetech.trainningJava.utils.BASE64DecodedMultipartFile;
import jakarta.annotation.Nullable;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Controller("mvcUserProductController")
@RequestMapping("/user/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping("/list")
    public ModelAndView listProduct(@Nullable @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("page", productService.findAllModel(page, 5, "name"));
//        throw new RuntimeException("Test");
        return modelAndView;
    }

    @GetMapping("/information")
    public ModelAndView GetInformation(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product/information");
        modelAndView.addObject("product", productService.getProductInforModel(id));
        return modelAndView;
    }
}