package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.enums.LogicalOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConditionModel{
    boolean isEnoughCondition;
    ProductEntity product;
    ConditionEntity conditionEntity;
    String description;
    String id;
    public ConditionModel(ConditionEntity conditionEntity, boolean isEnoughCondition, ProductEntity product) {
        this.id = conditionEntity.getId().toString() + "-" + product.getId().toString();
        this.isEnoughCondition = isEnoughCondition;
        this.product = product;
        this.conditionEntity = conditionEntity;
        ReloadDescription();
    }

    public void setEnoughCondition(boolean isEnoughCondition) {
        this.isEnoughCondition = isEnoughCondition;
        ReloadDescription();
    }

    void ReloadDescription() {
        this.description = conditionEntity.getDescription() + "của sản phẩm " + product.getName() + " " +
                (isEnoughCondition ? "đã đủ" : "chưa đủ") +
                " (điều kiện " + (conditionEntity.getLogicalOperator() == LogicalOperator.OR ? "đủ": "cần") + ")";
    }
}
