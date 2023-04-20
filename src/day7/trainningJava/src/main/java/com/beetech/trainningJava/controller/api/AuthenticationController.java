package com.beetech.trainningJava.controller.api;

import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.JwtTokenModel;
import com.beetech.trainningJava.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("apiAuthenticationController")
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        JwtTokenModel jwtTokenProvider = authService.getJwtTokenModelAfterAuthen(username, password);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "", jwtTokenProvider));
    }

    @PostMapping("/test")
    public ResponseEntity<ApiResponse> test() {
        return ResponseEntity.ok(new ApiResponse(true, "Test successfully"));
    }
}
