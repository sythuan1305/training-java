package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.DiscountEntity;
import com.beetech.trainningJava.model.DiscountModel;

import java.util.List;

public interface IDiscountService {
    List<DiscountEntity> getAll();

    DiscountEntity getOne(Integer id);

}
