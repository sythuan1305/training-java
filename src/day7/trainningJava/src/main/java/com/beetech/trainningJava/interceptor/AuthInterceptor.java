package com.beetech.trainningJava.interceptor;

import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.service.IAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Class này dùng để chặn trước khi vào controller
 * @see HandlerInterceptor
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private IAccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (accountService.isLogin()) {
            request.setAttribute("isAuthenticated", true);
            if (Role.ADMIN.equals(accountService.getAccountEntity().getRole())) {
                request.setAttribute("isAdmin", true);
            } else {
                request.setAttribute("isAdmin", false);
            }
        } else {
            request.setAttribute("isAuthenticated", false);
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
