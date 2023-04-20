package com.beetech.trainningJava.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtTokenModel {
    private String token;

    private long expiredTime;

    public JwtTokenModel(String token, long expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
    }
}
