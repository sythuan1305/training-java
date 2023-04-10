package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.enums.ConditionType;
import com.beetech.trainningJava.enums.LogicalOperator;
import com.beetech.trainningJava.enums.Operator;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.ConditionModel;
import com.beetech.trainningJava.service.IConditionService;
import org.springframework.stereotype.Service;

@Service
public class ConditionServiceImp implements IConditionService {
    @Override
    public ConditionModel getConditionModelByCartProductInforModel(CartProductInforModel cartProductInforModel, ConditionEntity conditionEntity) {
        ConditionModel conditionModel = new ConditionModel(conditionEntity, false, cartProductInforModel.getProduct());
        // check condition
        if (ConditionType.TOTAL_AMOUNT.equals(conditionEntity.getConditionType())) {
            switch (conditionEntity.getOperator()) {
                case GREATER_THAN -> {
                    if (cartProductInforModel.getPrice().compareTo(conditionEntity.getConditionValue()) > 0) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case GREATER_THAN_OR_EQUAL -> {
                    if (cartProductInforModel.getPrice().compareTo(conditionEntity.getConditionValue()) >= 0) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case LESS_THAN -> {
                    if (cartProductInforModel.getPrice().compareTo(conditionEntity.getConditionValue()) < 0) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case LESS_THAN_OR_EQUAL -> {
                    if (cartProductInforModel.getPrice().compareTo(conditionEntity.getConditionValue()) <= 0) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case EQUAL -> {
                    if (cartProductInforModel.getPrice().compareTo(conditionEntity.getConditionValue()) == 0) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                default -> {
                }
            }
        } else if (ConditionType.TOTAL_QUANTITY.equals(conditionEntity.getConditionType())) {
            switch (conditionEntity.getOperator()) {
                case GREATER_THAN -> {
                    if (cartProductInforModel.getQuantity() > conditionEntity.getConditionValue().intValue()) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case GREATER_THAN_OR_EQUAL -> {
                    if (cartProductInforModel.getQuantity() >= conditionEntity.getConditionValue().intValue()) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case LESS_THAN -> {
                    if (cartProductInforModel.getQuantity() < conditionEntity.getConditionValue().intValue()) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case LESS_THAN_OR_EQUAL -> {
                    if (cartProductInforModel.getQuantity() <= conditionEntity.getConditionValue().intValue()) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                case EQUAL -> {
                    if (cartProductInforModel.getQuantity() == conditionEntity.getConditionValue().intValue()) {
                        conditionModel.setEnoughCondition(true);
                    }
                }
                default -> {
                }
            }
        }
        System.out.println("conditionModel.isEnoughCondition() " + conditionModel.isEnoughCondition() + " " +
                "conditionModel.getConditionType()" + conditionModel.getConditionEntity().getConditionType() + " " +
                "cartProductInforModel.getProduct().getName()" + cartProductInforModel.getProduct().getName());
        return conditionModel;
    }
}
