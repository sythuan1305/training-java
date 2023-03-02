package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.jwt.JwtTokenProvider;
import com.beetech.trainningJava.model.JwtTokenModel;
import com.beetech.trainningJava.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements IAuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public JwtTokenModel login(String username, String password) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return new JwtTokenModel(jwtTokenProvider.generateToken(username), jwtTokenProvider.getExpiredTime());
    }
}
