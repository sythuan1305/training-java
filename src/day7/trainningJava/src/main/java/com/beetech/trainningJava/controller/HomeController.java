package com.beetech.trainningJava.controller;

import com.beetech.trainningJava.aspect.annotation.LogMemoryAndCpu;
import com.beetech.trainningJava.aspect.annotation.Loggable;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.utils.Utils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller này dùng để xử lý các request đến trang chủ
 */
@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Loggable
public class HomeController {
    private final ICartProductService cartProductService;

    private final IAccountService accountService;

    /**
     * Xử lý request đến trang chủ
     *
     * @param cartCookieValue giá trị của cookie cart
     * @param response response
     * @return trang chủ
     */
    @GetMapping("/")
    @Loggable
    public RedirectView readCartCookie(
            @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cartCookieValue,
            HttpServletResponse response) {
        // Lưu giỏ hàng vào database nếu đã đăng nhập và cookie cart khác giá trị mặc định
        if (accountService.isLogin() && !Utils.DEFAULT_COOKIE_VALUE.equals(cartCookieValue)) {
            cartProductService.saveCartProductEntityListWithAuthenticatedByCartProductParserList(
                    Utils.JsonParserListObjectWithEncodedURL(cartCookieValue));
            // Xóa cookie
            Utils.deleteCookie("cart", response);
        }

        // Chuyển hướng đến trang danh sách sản phẩm
        return new RedirectView("/user/product/list");
    }
}
