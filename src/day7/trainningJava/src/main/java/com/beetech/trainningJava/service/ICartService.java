package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.CartEntity;

import java.util.List;

/**
 * Interface chứa các method xư lý logic của cart
 * @see com.beetech.trainningJava.service.imp.CartServiceImp
 */
public interface ICartService {
    /**
     * Lưu thông tin cart entity vào database
     * @param cartEntity là 1 entity cần lưu vào database
     * @return CartEntity là thông tin của cart sau khi lưu vào database
     */
    public CartEntity saveCartEntity(CartEntity cartEntity);

    /**
     * Tìm cart entity theo account entity id
     * @param cartId là id của cart cần tìm
     * @return CartEntity là thông tin của cart sau khi tìm thấy
     */
    public CartEntity getCartEntityById(Integer cartId);

}
