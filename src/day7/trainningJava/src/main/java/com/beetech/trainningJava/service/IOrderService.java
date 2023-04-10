package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.model.OrderModel;

public interface IOrderService {

    OrderEntity save(OrderEntity orderEntity);

    OrderEntity findById(Integer id);

    OrderEntity save(OrderModel orderModel);
}
