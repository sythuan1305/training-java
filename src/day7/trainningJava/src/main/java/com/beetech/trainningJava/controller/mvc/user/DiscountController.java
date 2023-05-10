package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.IProductDiscountConditionService;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Controller này dùng để xử lý các request liên quan đến giảm giá
 */
@Controller("mvcUserDiscountController")
@RequestMapping("/user/discount")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class DiscountController {
    private final ICartProductService cartProductService;

    private final IProductDiscountConditionService discountProductService;

    /**
     * Chuyển đến trang xem danh sách giảm giá
     *
     * @param cookieValue   giá trị của cookie cart
     * @param cartProductId danh sách id sản phẩm trong giỏ hàng được chọn để mua
     * @return trang xem danh sách giảm giá
     */
    @PostMapping("/list")
    public ModelAndView information(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue,
            @RequestParam("cartProductId") Integer[] cartProductId) {
        // Lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductInforModel> cartProductInforModels = cartProductService
                .createCartProductInforListWithLoginOrNotWithCartProductParserList(Utils.JsonParserListObjectWithEncodedURL(cookieValue));

        // Lọc ra các sản phẩm được chọn để mua
        cartProductInforModels = cartProductService.getCartProductInforModelListByCartProductInforModelListAndCartProductArray(
                cartProductInforModels, cartProductId);

        // Lấy danh sách giảm giá
        List<DiscountModel> discountModels = discountProductService
                .getDiscountModelListByCartProductInforModelList(cartProductInforModels);

        // Chuyển đến trang xem danh sách giảm giá
        ModelAndView modelAndView = new ModelAndView("discount/list1");
        modelAndView.addObject("discounts", discountModels);
        modelAndView.addObject("cartProductIds", Arrays.asList(cartProductId));
        return modelAndView;
    }
}