package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
     * @param pageable tham số dùng để phân trang
     * @return trang danh sách sản phẩm
     */
    @GetMapping("/list")
    public ModelAndView listProduct(@PageableDefault(size = 5, sort = {"name"}) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("product/list");
        PageModel<ProductInforModel> pageModel = productService.findPageModelProductInforModelByPageIndex(pageable);
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