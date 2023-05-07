package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.model.OrderModel;
import com.beetech.trainningJava.repository.OrderRepository;
import com.beetech.trainningJava.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class này dùng để triển khai các phương thức của interface IOrderService
 * @see IOrderService
 */
@Service
public class OrderServiceImp implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderEntity saveOrderEntityByEntity(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity findOrderEntityById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public OrderEntity saveOrderEntityByModel(OrderModel orderModel) {
        OrderEntity orderEntity = new OrderEntity(orderModel);
        orderEntity = saveOrderEntityByEntity(orderEntity);
        orderModel.setId(orderEntity.getId());
        return orderEntity;
    }
}
