package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;

import java.util.List;

public interface ICartProductOrder {
    CartProductOrderEntity saveCartProductOrderEntityByEntity(CartProductOrderEntity cartProductOrderEntity);

    List<CartProductOrderEntity> updateCartProductOrderEntityListAfterBoughtByOrderEntity(OrderEntity orderEntity);

    List<CartProductOrderEntity> findCartProductOrderEntityListByOrderEntity(OrderEntity orderEntity);

    List<CartProductOrderEntity> saveCartProductOrderEntityListByEntityList(List<CartProductOrderEntity> cartProductOrderEntities);

    List<CartProductOrderEntity> saveCartProductOrderEntityListByEntityListAndOrderEntity(List<CartProductEntity> cartProductEntities, OrderEntity orderEntity);
}
