package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.DiscountEntity;

import java.util.List;

/**
 * Interface chứa các method xư lý logic của discount
 * @see com.beetech.trainningJava.service.imp.DiscountServiceImp
 */
public interface IDiscountService {
    /**
     * Lấy tất cả discount entity trong database
     * @return danh sách discount entity
     */
    List<DiscountEntity> getDistcountEntityList();

    /**
     * Lấy discount entity theo id
     * @param id là id của discount cần lấy
     * @return DiscountEntity là thông tin của discount sau khi lấy
     */
    DiscountEntity getDiscountEntity(Integer id);

}
