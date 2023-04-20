package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.DiscountEntity;

import java.util.List;

public interface IDiscountService {
    List<DiscountEntity> getDistcountEntityList();

    DiscountEntity getDiscountEntity(Integer id);

}
