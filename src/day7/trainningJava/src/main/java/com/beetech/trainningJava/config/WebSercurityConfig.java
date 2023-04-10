package com.beetech.trainningJava.config;

import com.beetech.trainningJava.enums.Role;
import com.beetech.trainningJava.filter.CustomAuthenticationFilter;
import com.beetech.trainningJava.jwt.JwtAuthenticationFilter;
import org.apache.tomcat.util.http.CookieProcessor;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
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

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true, jsr250Enabled = true)
public class WebSercurityConfig {

    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()).and().build();
    }



//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
//        return tomcatServletWebServerFactory -> tomcatServletWebServerFactory.addContextCustomizers(
//                 context -> {
//            context.setCookieProcessor(new Rfc6265CookieProcessor());
//        });
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/payment/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                );
        http
                .formLogin(form -> form.usernameParameter("username").passwordParameter("password")
                        .loginPage("/login")
                        .loginProcessingUrl("/auth/login")
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/", true).permitAll());
        http.
                logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout=true").permitAll();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
