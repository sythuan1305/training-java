package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.ConditionModel;

public interface IConditionService {
    ConditionModel getConditionModelByCartProductInforModel(CartProductInforModel cartProductInforModel, ConditionEntity conditionEntity);
}
