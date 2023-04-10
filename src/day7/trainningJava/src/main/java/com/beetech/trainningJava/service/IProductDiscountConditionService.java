package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.DiscountEntity;
import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.model.ProductsDiscountConditionsModel;

import java.util.List;

public interface IProductDiscountConditionService {
    List<ProductsDiscountConditionsModel> getProductDiscountConditionList();

    List<ProductDiscountConditionEntity> getListProductDiscountConditionByProductId(Integer productId);

    List<DiscountModel> getDiscountModels(List<CartProductInforModel> cartProductInforModels);

    DiscountModel getDiscountModel(Integer discountId, List<CartProductInforModel> cartProductInforModels);
}
