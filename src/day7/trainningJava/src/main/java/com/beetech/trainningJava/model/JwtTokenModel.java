package com.beetech.trainningJava.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Class này dùng để trả về token và thời gian hết hạn của token
 */
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
