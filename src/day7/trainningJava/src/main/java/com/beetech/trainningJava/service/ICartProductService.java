package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.model.CartProductInforModel;

import java.util.List;
import java.util.Map;

public interface ICartProductService {
    //for test
    CartProductEntity findCartProductEntityByCartProductEntity(CartProductEntity cartProductEntity);

    List<CartProductEntity> getCartProductListById(Integer cartId);

    List<CartProductEntity> getCartProductListByCartIdAndIsBought(Integer cartId, boolean isBought);

    List<CartProductInforModel> getCartProductInforListByCartProductEntityList(List<CartProductEntity> cartProductEntityList);

    List<CartProductEntity> saveCartProductEntityListWithAuthenticatedByCartProductParserList(List<Map<String, Object>> cartProductParsers);

    CartProductEntity saveCartProductEntityWithAuthenticatedByCartProductParserList(Map<String, Object> cartProductParser);

    CartProductEntity saveCartProductEntityWithUnAuthByCartProductEntity(CartProductEntity cartProductEntity);

    List<CartProductInforModel> getCartProductInforListByCartIdAndIsBought(Integer cartId, boolean isBought);

    List<CartProductInforModel> getCartProductInforListByCartProductParserList(List<Map<String, Object>> cartProductParserList);

    CartProductInforModel getCartProductInforByCartProductParser(Map<String, Object> cartProductParser);

    List<CartProductInforModel> getCartProductInforListByCartProductModelListAndCartProductArray(List<CartProductInforModel> cartProductInforModelList, Integer[] cartProducts);

    List<CartProductEntity> saveCartProductEntityListByCartProductModelList(List<CartProductInforModel> cartProductInforModelList);

    List<CartProductEntity> changeCartProductInforModelListToCartProductEntityList(List<CartProductInforModel> cartProductInforModelList);

    void TestMinusQuantity();

    void TestSave(boolean isException);
}
