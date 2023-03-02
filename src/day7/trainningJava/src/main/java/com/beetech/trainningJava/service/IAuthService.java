package com.beetech.trainningJava.service;

import com.beetech.trainningJava.model.JwtTokenModel;

public interface IAuthService {
    JwtTokenModel login(String username, String password);
}
