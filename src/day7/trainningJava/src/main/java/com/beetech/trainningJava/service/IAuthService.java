package com.beetech.trainningJava.service;

import com.beetech.trainningJava.model.JwtTokenModel;

public interface IAuthService {
    JwtTokenModel getJwtTokenModelAfterAuthen(String username, String password);
}
