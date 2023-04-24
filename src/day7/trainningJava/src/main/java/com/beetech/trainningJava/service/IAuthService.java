package com.beetech.trainningJava.service;

import com.beetech.trainningJava.model.JwtTokenModel;

/**
 * Interface chứa các method xư lý logic cho việc xác thực
 * @see com.beetech.trainningJava.service.imp.AuthServiceImp
 */
public interface IAuthService {
    /**
     * Lấy thông tin jwt token model sau khi xác thực
     * @param username là tên đăng nhập của user
     * @param password là mật khẩu của user
     * @return JwtTokenModel là thông tin của jwt token sau khi xác thực
     */
    JwtTokenModel getJwtTokenModelAfterAuthen(String username, String password);
}
