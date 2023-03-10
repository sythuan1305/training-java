package com.beetech.trainningJava.controller.mvc;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("mvcAuthenticationController")
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {

    @PostMapping("/login")
    public ModelAndView login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("POST_LOGIN"  + auth.isAuthenticated());
        if (auth.isAuthenticated()) {
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("/login");
    }
}
