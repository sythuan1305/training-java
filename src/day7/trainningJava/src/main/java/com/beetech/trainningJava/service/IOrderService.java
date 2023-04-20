package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.model.OrderModel;

public interface IOrderService {

    OrderEntity saveOrderEntityByEntity(OrderEntity orderEntity);

    OrderEntity findOrderEntityById(Integer id);

    OrderEntity saveOrderEntityByModel(OrderModel orderModel);
}
