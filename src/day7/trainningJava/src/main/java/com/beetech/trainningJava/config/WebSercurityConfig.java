package com.beetech.trainningJava.config;

import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.jwt.JwtAuthenticationFilter;
import com.beetech.trainningJava.security.AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Cấu hình bảo mật cho ứng dụng
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@EnableMethodSecurity()
public class WebSercurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    // Khởi tạo bean password encoder để mã hóa mật khẩu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Khởi tạo bean authentication manager để quản lý authentication
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder()).and().build();
    }

    // Khởi tạo bean cookie same site supplier để cấu hình cookie
    // ngăn chặn tấn công CSRF
    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict();
    }

    // Khởi tạo bean cors configuration source để cấu hình CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Khởi tạo bean security filter chain để lọc các request
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Cấu hình header
        http.headers(
                headers -> headers
                        .xssProtection()
                        .and()
                        // cấu hình header content security policy
                        // để ngăn chặn tấn công XSS
                        // cho phép những script nào được load
                        .contentSecurityPolicy("" +
                                "img-src 'self' data: " +
                                ";" +
                                "default-src 'self' " +
                                ";" +
                                "script-src " +
                                "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js " +
                                "https://cdn.jsdelivr.net/npm/papaparse@5.3.2/papaparse.min.js " +
                                "https://unpkg.com/read-excel-file@5.x/bundle/read-excel-file.min.js " +
                                "'unsafe-inline' " +
                                "http://localhost:8080/static/js/cookie.js " +
                                "http://localhost:8080/static/js/common.js " +
                                "http://localhost:8080/static/js/product/upload.js " +
                                "http://localhost:8080/static/js/product/list.js " +
                                "http://localhost:8080/static/js/discount.js " +
                                "http://localhost:8080/static/js/cart/information-cart.js " +
                                "http://localhost:8080/static/js/cart/add-product.js " +
                                "http://localhost:8080/static/js/auth/login.js " +
                                ";" +
                                "style-src https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css 'unsafe-inline'" +
                                ";"
                        )

        );

        // Cấu hình CSRF
        // Ngăn chặn tấn công CSRF
        // Cho phép tất cả các request đến /api/** không cần xác thực CSRF
        http.csrf().ignoringRequestMatchers("/api/**");

        // Cấu hình CORS
        http.cors().configurationSource(corsConfigurationSource()); // TODO - Nghiên cứu lại cấu hình CORS
//        http.cors();

        // Cấu hình authorize và authentication
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/api/admin/**").hasAuthority(Role.ADMIN.name())
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/user/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/user/**").permitAll()
                .requestMatchers("/payment/**").permitAll()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
        );

        // Cấu hình login
        http.formLogin(form -> form.usernameParameter("username").passwordParameter("password")
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler)
                .loginProcessingUrl("/auth/login-process")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/", true).permitAll());
        // Cấu hình logout
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
