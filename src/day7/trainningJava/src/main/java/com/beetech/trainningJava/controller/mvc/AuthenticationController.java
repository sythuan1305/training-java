package com.beetech.trainningJava.controller.mvc;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller này dùng để xử lý các request liên quan đến xác thực
 */
@Controller("mvcAuthenticationController")
@RequestMapping("/auth")
@Log4j2
public class AuthenticationController {
}
