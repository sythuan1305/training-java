package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.entity.DiscountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Hashtable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDiscountConditionsModel {
    DiscountEntity discount;

    Hashtable<Integer, ConditionDetailModel> conditionDetailModels;
}
