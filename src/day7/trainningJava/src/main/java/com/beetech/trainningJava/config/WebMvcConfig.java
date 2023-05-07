package com.beetech.trainningJava.config;

import com.beetech.trainningJava.interceptor.AuthInterceptor;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Cấu hình web mvc cho ứng dụng
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    // Thêm các view controller
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/user/product/list");
        registry.addViewController("/login").setViewName("auth/login");
        registry.addViewController("/logout").setViewName("auth/login");
    }

    // Thêm các interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**");

    }

    // Khởi tạo bean auth interceptor
    // để kiểm tra quyền truy cập của người dùng
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    // render view
    // Thêm view resolver
    // dùng để render view theo cấu hình
    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(thymeleafTemplateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    // render template
    // Thêm các template engine
    // dùng để render template theo cấu hình
    // template là các file html
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(thymeleafTemplateResolver());
        engine.addDialect(layoutDialect());
        return engine;
    }

    //load template
    // Thêm các template resolver
    // dùng để load template theo cấu hình
    // template là các file html
    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode(TemplateMode.HTML);
        return resolver;
    }

    //layout
    // Thêm layout dialect
    // dùng để chia bố cục cho các trang
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    // Thêm các resource handler
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/assets/**")) {
            registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        }
    }
}