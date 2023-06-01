package com.beetech.trainningJava.controller.api;

import com.beetech.trainningJava.exception.HttpError;
import com.beetech.trainningJava.model.AccountRegisterDto;
import com.beetech.trainningJava.model.ApiResponse;
import com.beetech.trainningJava.model.JwtTokenModel;
import com.beetech.trainningJava.service.IAuthService;
import com.beetech.trainningJava.utils.Utils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
//@Validated
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController {
    private final IAuthService authService;

    /**
     * Xử lý request đến api login
     *
     * @param username tên đăng nhập
     * @param password mật khẩu
     * @return response entity chứa ApiResponse <br>
     * ApiResponse chứa JwtTokenModel
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestParam("username") String username,
                                             @RequestParam("password") String password,
                                             @CookieValue(value = "cart", defaultValue = Utils.DEFAULT_COOKIE_VALUE) String cartCookieValue,
                                             HttpServletResponse response) {
        JwtTokenModel jwtTokenProvider = authService.getJwtTokenModelAfterAuthen(username, password, cartCookieValue, response);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "", jwtTokenProvider));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken(
            @CookieValue(name = "refresh-token", defaultValue = "default-cookie") String refreshToken,
            @RequestHeader("Authorization") String accessToken,
            HttpServletResponse response) {

        if (!accessToken.startsWith("Bearer"))
            throw new HttpError("Invalid authorization header", HttpStatus.UNAUTHORIZED);

        JwtTokenModel newAccessToken = authService.reCreateJwtTokenModel(refreshToken, accessToken.substring(7), response);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "refresh success", newAccessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody @Valid AccountRegisterDto accountRegisterDto) {
        authService.register(accountRegisterDto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "register success", null));
    }

    @GetMapping(value = "/register/verify", params = {"token"})
    public ResponseEntity<Object> verifyRegisterToken(
            @RequestParam("token") String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "verify success", null));
    }

    @PostMapping("/password/forgot")
    public ResponseEntity<Object> forgotPassword(
            @RequestParam("email") @Email @NotBlank @NotNull String email) {
        authService.sendPasswordResetToken(email);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "forgot password success", null));
    }

    // test token is valid in browser
    @GetMapping(value = "/password/reset", params = {"token"})
    public ResponseEntity<Object> verifyResetPasswordToken(
            @RequestParam("token") String token) {
        authService.validateResetPasswordToken(token);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "verify success", null));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<Object> resetPassword(
            @RequestParam("token") @NotNull @NotBlank String token,
            @RequestParam("password") @NotNull @NotBlank String password) {
        authService.resetPassword(token, password);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), true, "reset password success", null));
    }
}
