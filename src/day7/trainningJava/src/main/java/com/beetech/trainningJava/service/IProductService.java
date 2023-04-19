package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;

import java.util.List;

public interface IProductService {
    ProductEntity save(ProductEntity productEntity);
    PageModel<ProductEntity> findAll(Integer page, Integer size, String sort);

    PageModel<ProductInforModel> findAllModel(Integer page, Integer size, String sort);
    ProductEntity getOne(Integer id);

    ProductInforModel getProductInforModel(Integer id);

    List<ProductEntity> getProductListByDiscountId(Integer discountId);

    ProductEntity findByProductName(String productName);

    void TestMinusQuantity(Integer number);

}
