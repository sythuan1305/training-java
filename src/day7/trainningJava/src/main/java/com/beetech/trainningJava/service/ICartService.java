package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;

import java.util.List;

public interface ICartService {
    public CartEntity save(CartEntity cartEntity);
    public CartEntity getOne(Integer cartId);
    public List<CartEntity> findAll();
    public CartEntity update(CartEntity cartEntity);
    public void delete(Integer cartId);

    public CartEntity checkCartIdNullAndCreateNewIfNull(Integer cartId);

    public void saveAccountToCart(AccountEntity accountEntity, CartEntity cartEntity);
}
