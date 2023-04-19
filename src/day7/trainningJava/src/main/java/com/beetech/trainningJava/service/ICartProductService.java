package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.model.CartProductInforModel;

import java.util.List;
import java.util.Map;

public interface ICartProductService {
//    public PageModel<CartEntity> findAll();
//    public CartIn4Model save(CartIn4Model cartIn4Model);
    //for test
    CartProductEntity findByCartProductEntity(CartProductEntity cartProductEntity);
    CartProductEntity findByCartProductId(Integer cartProductId);
    public List<CartProductEntity> getLisCartProductByCartId(Integer cartId);
    public List<CartProductEntity> getListCartProductByCartIdAndIsBought(Integer cartId, boolean isBought);
    public List<CartProductInforModel> getListCartProductInfor(List<CartProductEntity> cartProductEntityList);

    public List<CartProductEntity> savesWithAuthenticated(List<Map<String, Object>> cartProductParser);

    public CartProductEntity saveWithAuthenticated(Map<String, Object> cartProductParser);

    public CartProductEntity saveWithUnAuth(CartProductEntity cartProductEntity);

    public CartProductEntity savesWithUnAuth(List<CartProductEntity> cartProductEntityList);

    List<CartProductInforModel> getLisCartProductInforByCartId(Integer cartId);

    List<CartProductInforModel> getListCartProductInforByCartIdAndIsBought(Integer cartId, boolean isBought);

    List<CartProductInforModel> getListCartProductInforWithParser(List<Map<String, Object>> cartProductParserList);

    CartProductInforModel getCartProductInforWithParser(Map<String, Object> cartProductParser);

    List<CartProductInforModel> getListCartProductInforWithCartProductArray(List<CartProductInforModel> cartProductInforModelList, Integer[] cartProducts);
    List<CartProductEntity> saveCartProductInforModelList(List<CartProductInforModel> cartProductInforModelList);

    List<CartProductEntity> changeCartProductInforToCartProductEntity(List<CartProductInforModel> cartProductInforModelList);
    void TestMinusQuantity();

    void TestSave(boolean isException);
}
