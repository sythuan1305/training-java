package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.IProductService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ProductController {
    private final IProductService productService;

    /**
     * Xử lý request đến trang danh sách sản phẩm
     * @param page trang hiện tại
     * @return trang danh sách sản phẩm
     */
    @GetMapping("/list")
    public ModelAndView listProduct(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        PageModel<ProductInforModel> pageModel = productService.findPageModelProductInforModelByPageIndex(page, 5, "name");
        modelAndView.addObject("page", pageModel);
        return modelAndView;
    }

    /**
     * Xử lý request đến trang chi tiết sản phẩm
     * @param id id sản phẩm
     * @return trang chi tiết sản phẩm
     */
    @GetMapping("/information")
    public ModelAndView GetInformation(@RequestParam("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("product/information");
        modelAndView.addObject("product", productService.getProductInforModelById(id));
        return modelAndView;
    }
}