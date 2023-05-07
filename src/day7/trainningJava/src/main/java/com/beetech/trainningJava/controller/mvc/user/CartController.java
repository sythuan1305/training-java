package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.aspect.annotation.Loggable;
import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.service.*;
import com.beetech.trainningJava.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

/**
 * Controller này dùng để xử lý các request liên quan đến giỏ hàng
 */
@Controller("mvcUserCartController")
@RequestMapping("/user/cart")
@Loggable
public class CartController {
    @Autowired
    private ICartProductService cartProductService;

    @Autowired
    private IProductService productService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IProductDiscountConditionService productDiscountConditionService;

    /**
     * Chuyển đến trang xem thông tin giỏ hàng
     *
     * @param cookieValue giá trị của cookie cart
     * @return trang xem thông tin giỏ hàng
     * @deprecated được thay thế bởi {@link #informationCart(String)}
     */
    @Deprecated
    @GetMapping("/information")
    public ModelAndView information(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue,
            @RequestParam(value = "discountId", required = false) Integer discountId) {
        ModelAndView modelAndView = new ModelAndView("cart/information");

        // Lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductInforModel> cartProductInforModels = new ArrayList<>();
        if (accountService.isLogin()) {
            cartProductInforModels = cartProductService
                    .getCartProductInforListByCartIdAndIsBought(accountService.getCartEntity().getId(), false);
            modelAndView.addObject("cartProducts", Utils.JsonParserString(cartProductInforModels));
        } else {
            if (!Utils.DEFAULT_COOKIE_VALUE.equals(cookieValue)) {
                cartProductInforModels = cartProductService.getCartProductInforListByCartProductParserList(
                        Utils.JsonParserListObjectWithEncodedURL(cookieValue));
                modelAndView.addObject("cartProducts", Utils.JsonParserString(cartProductInforModels));
            } else {
                modelAndView.addObject("cartProducts", new ArrayList<CartProductEntity>());
            }
        }

        // Lấy thông tin giảm giá
        DiscountModel discountModel = productDiscountConditionService.getDiscountModelByDiscountIdAndCartProductInforList(discountId,
                cartProductInforModels);

        modelAndView.addObject("discount", discountModel == null ? null : Utils.JsonParserString(discountModel));
        return modelAndView;
    }

    /**
     * Chuyển đến trang xem thông tin giỏ hàng
     *
     * @param cookieValue giá trị của cookie cart
     * @return trang xem thông tin giỏ hàng
     */
    @GetMapping("/information-cart")
    public ModelAndView informationCart(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cookieValue) {
        // for test
        cartProductService.findCartProductEntityByCartProductEntity(new CartProductEntity());
        // end for test

        // Lấy thông tin giỏ hàng từ cookie hoặc database
        List<CartProductInforModel> cartProductInforModels = cartProductService.createCartProductInforListWithLoginOrNotWithCartProductParserList(
                Utils.JsonParserListObjectWithEncodedURL(cookieValue)
        );

        // Tạo model and view cho trang thông tin giỏ hàng
        ModelAndView modelAndView = new ModelAndView("cart/information-cart");
        modelAndView.addObject("cartProducts", cartProductInforModels);
        return modelAndView;
    }

    /**
     * Chuyển đến trang thêm sản phẩm vào giỏ hàng với phương thức GET
     *
     * @param productId id của sản phẩm
     * @return trang thêm sản phẩm vào giỏ hàng
     */
    @GetMapping("/add")
    public ModelAndView add(@RequestParam("productId") Integer productId) {
        ModelAndView modelAndView = new ModelAndView("cart/add-product");
        modelAndView.addObject("product", productService.getProductInforModelById(productId));
        return modelAndView;
    }

    /**
     * Chuyển hướng đến trang thông tin giỏ hàng sau khi thêm sản phẩm vào giỏ hàng
     *
     * @param cartProduct giá trị của cart json
     * @return trang thông tin giỏ hàng
     */
    @PostMapping("/add")
    public RedirectView add(@RequestParam("cartProduct") String cartProduct) throws ParseException {
        // Nếu chưa đăng nhập thì chuyển hướng đến trang thông tin giỏ hàng
        // và lưu giá trị của cart json vào cookie
        if (!accountService.isLogin()) {
            return new RedirectView("/user/cart/information-cart");
        }

        // Nếu đã đăng nhập thì lưu giá trị của cart json vào database
        cartProductService.saveCartProductEntityListWithAuthenticatedByCartProductParserList(
                (List<Map<String, Object>>) new JSONParser(cartProduct).parse());
        return new RedirectView("/user/cart/information-cart");
    }
}
