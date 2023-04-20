package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;

import java.util.List;

public interface ICartService {
    public CartEntity saveCartEntity(CartEntity cartEntity);

    public CartEntity getCartEntityById(Integer cartId);

}
