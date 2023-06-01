package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.model.ConditionEntityDto;
import com.beetech.trainningJava.enums.ConditionType;
import com.beetech.trainningJava.model.CartProductEntityDto;
import com.beetech.trainningJava.model.CartProductInforModel;
import com.beetech.trainningJava.model.ConditionModel;
import com.beetech.trainningJava.service.IConditionService;
import org.springframework.stereotype.Service;

/**
 * Class này dùng để triển khai các phương thức của interface IConditionService
 *
 * @see IConditionService
 */
@Service
public class ConditionServiceImp implements IConditionService {
    @Override
    public ConditionModel getConditionModelByCartProductInforModelAndConditionEntity(
            CartProductInforModel cartProductInforModel, ConditionEntity conditionEntity) {
        ConditionModel conditionModel = new ConditionModel(conditionEntity, false, cartProductInforModel.getProduct());
        // Kiểm tra điều kiện
        // Nếu điều kiện đủ thì set enoughCondition = true
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
                    // DO NOTHING
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
                    // DO NOTHING
                }
            }
        }
        return conditionModel;
    }

    @Override
    public ConditionEntityDto getConditionEntityDtoByCartProductEntityDtoAndConditionEntity(
            CartProductEntityDto cartProductEntityDto, ConditionEntity conditionEntity) {
        ConditionEntityDto conditionEntityDto =
                new ConditionEntityDto(conditionEntity, false, cartProductEntityDto.getProduct().getName());
        // Kiểm tra điều kiện
        // Nếu điều kiện đủ thì set enoughCondition = true
        if (ConditionType.TOTAL_AMOUNT.equals(conditionEntity.getConditionType())) {
            switch (conditionEntity.getOperator()) {
                case GREATER_THAN -> {
                    if (cartProductEntityDto.getPrice().compareTo(conditionEntity.getConditionValue()) > 0) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case GREATER_THAN_OR_EQUAL -> {
                    if (cartProductEntityDto.getPrice().compareTo(conditionEntity.getConditionValue()) >= 0) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case LESS_THAN -> {
                    if (cartProductEntityDto.getPrice().compareTo(conditionEntity.getConditionValue()) < 0) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case LESS_THAN_OR_EQUAL -> {
                    if (cartProductEntityDto.getPrice().compareTo(conditionEntity.getConditionValue()) <= 0) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case EQUAL -> {
                    if (cartProductEntityDto.getPrice().compareTo(conditionEntity.getConditionValue()) == 0) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                default -> {
                    // DO NOTHING
                }
            }
        } else if (ConditionType.TOTAL_QUANTITY.equals(conditionEntity.getConditionType())) {
            switch (conditionEntity.getOperator()) {
                case GREATER_THAN -> {
                    if (cartProductEntityDto.getQuantity() > conditionEntity.getConditionValue().intValue()) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case GREATER_THAN_OR_EQUAL -> {
                    if (cartProductEntityDto.getQuantity() >= conditionEntity.getConditionValue().intValue()) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case LESS_THAN -> {
                    if (cartProductEntityDto.getQuantity() < conditionEntity.getConditionValue().intValue()) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case LESS_THAN_OR_EQUAL -> {
                    if (cartProductEntityDto.getQuantity() <= conditionEntity.getConditionValue().intValue()) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                case EQUAL -> {
                    if (cartProductEntityDto.getQuantity() == conditionEntity.getConditionValue().intValue()) {
                        conditionEntityDto.setIsEnoughCondition(true);
                    }
                }
                default -> {
                    // DO NOTHING
                }
            }
        }
        return conditionEntityDto;
    }
}
