package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.DiscountModel;
import com.beetech.trainningJava.model.ProductsDiscountConditionsModel;

import java.util.List;

public interface IProductDiscountConditionService {
    List<ProductsDiscountConditionsModel> getProductDiscountConditionList();

    List<ProductDiscountConditionEntity> getProductDiscountConditionListByProductId(Integer productId);

    List<DiscountModel> getDiscountModelListByCartProductInforModelList(List<CartProductInforModel> cartProductInforModels);

    DiscountModel getDiscountModelByCartProductInforList(Integer discountId, List<CartProductInforModel> cartProductInforModels);
}
