package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.model.OrderModel;

/**
 * Interface của order service
 * @see com.beetech.trainningJava.service.imp.OrderServiceImp
 */
public interface IOrderService {
    /**
     * Lưu order entity vào database
     * @param orderEntity là 1 entity cần lưu vào database
     * @return OrderEntity là thông tin của order sau khi lưu vào database
     */
    OrderEntity saveOrderEntityByEntity(OrderEntity orderEntity);

    /**
     * Tìm order entity theo id
     * @param id là id của order cần tìm
     * @return OrderEntity là thông tin của order sau khi tìm thấy
     */
    OrderEntity findOrderEntityById(Integer id);

    /**
     * Lưu order model vào database
     * @param orderModel là 1 model cần lưu vào database
     * @return OrderEntity là thông tin của order sau khi lưu vào database
     */
    OrderEntity saveOrderEntityByModel(OrderModel orderModel);
}
