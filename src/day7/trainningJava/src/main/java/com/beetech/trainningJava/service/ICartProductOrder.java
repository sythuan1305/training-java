package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;

import java.util.List;

public interface ICartProductOrder {

    CartProductOrderEntity save(CartProductOrderEntity cartProductOrderEntity);
    List<CartProductOrderEntity> updateCartProductOrderAfterBought(OrderEntity orderEntity);
    List<CartProductOrderEntity> findAllByOrder(OrderEntity orderEntity);
    List<CartProductOrderEntity> saveAll(List<CartProductOrderEntity> cartProductOrderEntities);
    List<CartProductOrderEntity> saveAll(List<CartProductEntity> cartProductEntities, OrderEntity orderEntity);
}
