package com.beetech.trainningJava.service;

import com.beetech.trainningJava.model.AccountRegisterDto;
import com.beetech.trainningJava.model.JwtTokenModel;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Interface chứa các method xư lý logic cho việc xác thực
 *
 * @see com.beetech.trainningJava.service.imp.AuthServiceImp
 */
public interface IAuthService {
    /**
     * Lấy thông tin jwt token model sau khi xác thực
     *
     * @param username là tên đăng nhập của user
     * @param password là mật khẩu của user
     * @param cartCookie là cookie chứa thông tin giỏ hàng
     * @param response là http response
     * @return JwtTokenModel là thông tin của jwt token sau khi xác thực
     */
    JwtTokenModel getJwtTokenModelAfterAuthen(String username, String password, String cartCookie, HttpServletResponse response);

    /**
     * Thêm cookie chứa refresh token vào http response
     *
     * @param email       là email của user
     * @param accessToken là access token của user
     * @param response    là http response
     */
    void addRefreshTokenCookie(String email, String accessToken, HttpServletResponse response);

    /**
     * Tạo lại refresh token
     *
     * @param refreshToken là refresh token cũ
     * @param accessToken  là access token cũ
     * @param response     là http response
     * @return String là refresh token mới
     */
    JwtTokenModel reCreateJwtTokenModel(String refreshToken, String accessToken, HttpServletResponse response);

    void register(AccountRegisterDto accountRegisterDto);

    void verifyAccount(String token);

    void sendPasswordResetToken(String email);

    void validateResetPasswordToken(String token);

    void resetPassword(String token, String newPassword);

    void saveCartFromCookieToDB(String cartCookie, HttpServletResponse response);
}
