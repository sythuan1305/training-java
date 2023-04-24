package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.ConditionModel;

/**
 * Interface chứa các method xư lý logic của condition
 * @see com.beetech.trainningJava.service.imp.ConditionServiceImp
 */
public interface IConditionService {
    /**
     * Lấy thông tin condition model sau khi kiểm tra điều kiện đủ hay chưa của sản phẩm trong giỏ hàng
     * @param cartProductInforModel là thông tin của sản phẩm trong giỏ hàng
     * @param conditionEntity là thông tin của điều kiện
     * @return ConditionModel là thông tin của điều kiện của sản phẩm sau khi kiểm tra điều kiện đủ hay chưa
     */
    ConditionModel getConditionModelByCartProductInforModelAndConditionEntity(CartProductInforModel cartProductInforModel, ConditionEntity conditionEntity);
}
