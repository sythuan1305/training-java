package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentMethod;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.repository.OrderRepository;
import com.beetech.trainningJava.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public OrderEntity save(OrderModel orderModel) {
        OrderEntity orderEntity = new OrderEntity(orderModel);
        orderEntity = save(orderEntity);
        orderModel.setId(orderEntity.getId());
        return orderEntity;
    }


}
