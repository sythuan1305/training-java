package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;

import java.util.List;

/**
 * Interface chứa các method xư lý logic của cart product order
 * @see com.beetech.trainningJava.service.imp.CartProductOrderImp
 */
public interface ICartProductOrder {
    /**
     * Lưu thông tin cart product order entity vào database
     *
     * @param cartProductOrderEntity là 1 entity cần lưu vào database
     * @return CartProductOrderEntity là thông tin của cart product order sau khi lưu vào database
     */
    CartProductOrderEntity saveCartProductOrderEntityByEntity(CartProductOrderEntity cartProductOrderEntity);

    /**
     * Cập nhật thông tin những cart product order entity sau khi mua
     *
     * @param orderEntity là order entity dùng để cập nhật thông tin cho cart product order entity
     * @return danh sách cart product order entity đã mua sau khi cập nhật
     */
    List<CartProductOrderEntity> updateCartProductOrderEntityListAfterBoughtByOrderEntity(OrderEntity orderEntity);

    /**
     * Tìm danh sách cart product order entity theo order entity
     *
     * @param orderEntity là order entity dùng để tìm danh sách cart product order entity theo order entity
     * @return danh sách cart product order entity theo order entity
     */
    List<CartProductOrderEntity> findCartProductOrderEntityListByOrderEntity(OrderEntity orderEntity);

    /**
     * Lưu danh sách cart product order entity vào database
     *
     * @param cartProductOrderEntities là danh sách cart product order entity cần lưu vào database
     * @return danh sách cart product order entity sau khi lưu vào database
     */
    List<CartProductOrderEntity> saveCartProductOrderEntityListByEntityList(List<CartProductOrderEntity> cartProductOrderEntities);

    /**
     * Tạo danh sách cart product order entity từ danh sách cart product entity và order entity, sau đó lưu vào database
     * @param cartProductEntities là danh sách cart product entity dùng để tạo danh sách cart product order entity
     * @param orderEntity là order entity dùng để tạo danh sách cart product order entity
     * @return danh sách cart product order entity sau khi lưu vào database
     */
    List<CartProductOrderEntity> saveCartProductOrderEntityListByCartProductEntityListAndOrderEntity(List<CartProductEntity> cartProductEntities, OrderEntity orderEntity);
}
