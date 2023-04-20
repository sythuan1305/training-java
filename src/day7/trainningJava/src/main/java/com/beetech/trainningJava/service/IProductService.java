package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;

import java.util.List;

public interface IProductService {
    ProductEntity saveProductEntity(ProductEntity productEntity);

    PageModel<ProductEntity> findPageModelByProductEntityIndex(Integer pageIndex, Integer size, String sort);

    PageModel<ProductInforModel> findPageModelByProductInforModelIndex(Integer pageIndex, Integer size, String sort);
    ProductEntity getProductEntityById(Integer id);

    ProductInforModel getProductInforModelById(Integer id);

    List<ProductEntity> getProductEntityListByDiscountId(Integer discountId);

    ProductEntity findProductEntityByProductName(String productName);

    void TestMinusQuantity(Integer number);
}
