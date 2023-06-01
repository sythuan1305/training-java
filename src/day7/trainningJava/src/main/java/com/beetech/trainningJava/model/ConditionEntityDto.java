package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.enums.ConditionType;
import com.beetech.trainningJava.enums.LogicalOperator;
import com.beetech.trainningJava.enums.Operator;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link ConditionEntity}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionEntityDto implements Serializable {
    ConditionType conditionType;
    BigDecimal conditionValue;
    Operator operator;
    LogicalOperator logicalOperator;
    String description;
    String descriptionAfter;
    String id;
    Boolean isEnoughCondition;
    String productName;

    public ConditionEntityDto(ConditionEntity conditionEntity, Boolean isEnoughCondition, String productName) {
        this.id = conditionEntity.getId().toString() + "-" + productName;
        this.isEnoughCondition = isEnoughCondition;
        this.productName = productName;
        this.conditionType = conditionEntity.getConditionType();
        this.conditionValue = conditionEntity.getConditionValue();
        this.operator = conditionEntity.getOperator();
        this.logicalOperator = conditionEntity.getLogicalOperator();
        this.description = conditionEntity.getDescription();
        ReloadDescription();
    }

    public void setIsEnoughCondition(boolean isEnoughCondition) {
        this.isEnoughCondition = isEnoughCondition;
        ReloadDescription();
    }

    void ReloadDescription() {
        this.descriptionAfter = this.getDescription() + " của sản phẩm " + this.getProductName() + " " +
                (isEnoughCondition ? "đã đủ" : "chưa đủ") +
                " (điều kiện " + (LogicalOperator.OR.equals(this.getLogicalOperator()) ? "đủ" : "cần") + ")";
    }
}