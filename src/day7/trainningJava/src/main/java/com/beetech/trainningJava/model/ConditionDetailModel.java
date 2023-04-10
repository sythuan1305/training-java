package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConditionDetailModel extends ConditionEntity {
    List<ProductEntity> products;
    public ConditionDetailModel(ConditionEntity conditionEntity, List<ProductEntity> products) {
        super(conditionEntity);
        this.products = products;
    }
}
