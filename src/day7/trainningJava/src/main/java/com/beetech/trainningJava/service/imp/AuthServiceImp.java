package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.jwt.JwtTokenProvider;
import com.beetech.trainningJava.model.JwtTokenModel;
import com.beetech.trainningJava.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Class này dùng để implement interface IAuthService
 * 
 * @see IAuthService
 */
@Service
@RequiredArgsConstructor(onConstructor_ = { @Autowired })
public class AuthServiceImp implements IAuthService {
    private final AuthenticationManager authManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtTokenModel getJwtTokenModelAfterAuthen(String username, String password) {
        // Xác thực từ username và password.
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Và trả về jwt token cho người dùng.
        return new JwtTokenModel(jwtTokenProvider.generateToken(username), jwtTokenProvider.getExpiredTime());
    }
}
