package com.beetech.trainningJava.controller.api;

import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.JwtTokenModel;
import com.beetech.trainningJava.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller này dùng để xử lý các request đến những api liên quan đến authentication
 */
@RestController("apiAuthenticationController")
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController {
    private final IAuthService authService;

    /**
     * Xử lý request đến api login
     * @param username tên đăng nhập
     * @param password mật khẩu
     * @return response entity chứa ApiResponse <br>
     * ApiResponse chứa JwtTokenModel
     */
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
