package com.beetech.trainningJava.security;

import com.beetech.trainningJava.jwt.JwtTokenProvider;
import com.beetech.trainningJava.service.IAuthService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.utils.Utils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final ICartProductService cartProductService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationSuccessHandler(ICartProductService cartProductService,
                                        JwtTokenProvider jwtTokenProvider) {
        this.cartProductService = cartProductService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authentication) throws IOException, ServletException {
        // Lưu giỏ hàng vào database nếu đã đăng nhập và cookie cart khác giá trị mặc định
        var cartCookie = WebUtils.getCookie(request, "cart");
        if (cartCookie != null) {
            {
                cartProductService.saveCartProductEntityListWithAuthenticatedByCartProductParserList(
                        Utils.JsonParserListObjectWithEncodedURL(cartCookie.getValue()));
                // Xóa cookie
                Utils.deleteCookie("cart", response);
            }
        }
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
