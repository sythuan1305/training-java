package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.PageModel;

public interface IProductService {
    ProductEntity save(ProductEntity productEntity);
    PageModel<ProductEntity> findAll(Integer page, Integer size, String sort);

    ProductEntity getOne(Integer id);
}
