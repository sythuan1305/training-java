package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.aspect.annotation.Loggable;
import com.beetech.trainningJava.service.IProductService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller này dùng để xử lý các request liên quan đến sản phẩm với quyền user
 */
@Controller("mvcUserProductController")
@RequestMapping("/user/product")
@Loggable
public class ProductController {
    @Autowired
    private IProductService productService;

    /**
     * Xử lý request đến trang danh sách sản phẩm
     * @param page trang hiện tại
     * @return trang danh sách sản phẩm
     */
    @GetMapping("/list")
    public ModelAndView listProduct(@Nullable @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        modelAndView.addObject("page", productService.findPageModelProductInforModelByPageIndex(page, 5, "name"));
        return modelAndView;
    }

    /**
     * Xử lý request đến trang chi tiết sản phẩm
     * @param id id sản phẩm
     * @return trang chi tiết sản phẩm
     */
    @GetMapping("/information")
    @Loggable
    public ModelAndView GetInformation(@Loggable @RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product/information");
        modelAndView.addObject("product", productService.getProductInforModelById(id));
        return modelAndView;
    }
}