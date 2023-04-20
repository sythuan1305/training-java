package com.beetech.trainningJava.config;

import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true, jsr250Enabled = true)
public class WebSercurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()).and().build();
    }

    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofStrict();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers(
                        headers -> headers
                                .xssProtection()
                                .and()
                                .contentSecurityPolicy("form-action 'self'; " +
                                        "img-src 'self' data:; " +
                                        "default-src 'self'; " +
                                        "script-src " +
                                        "https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js " +
                                        "https://cdn.jsdelivr.net/npm/papaparse@5.3.2/papaparse.min.js " +
                                        "https://unpkg.com/read-excel-file@5.x/bundle/read-excel-file.min.js " +
                                        "'unsafe-inline'; " +
                                        "style-src https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css 'unsafe-inline'")

                )
                .csrf().ignoringRequestMatchers("/api/**").and()
                .cors().and()
                .authorizeHttpRequests(request -> request
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
        http
                .formLogin(form -> form.usernameParameter("username").passwordParameter("password")
                        .loginPage("/login")
                        .loginProcessingUrl("/auth/login-process")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/", true).permitAll());
        http.
                logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
