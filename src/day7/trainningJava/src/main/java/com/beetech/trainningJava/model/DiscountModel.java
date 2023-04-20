package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ConditionEntity;
import com.beetech.trainningJava.entity.DiscountEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Hashtable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountModel extends DiscountEntity {
    Hashtable<String, ConditionModel> conditions;

    boolean isAbleToUse;

    @JsonBackReference
    ZonedDateTime startDateZone;

    @JsonBackReference
    ZonedDateTime endDateZone;

    public DiscountModel(DiscountEntity discountEntity, Hashtable<String, ConditionModel> conditions, boolean isAbleToUse) {
        super(discountEntity);
        this.conditions = conditions;
        this.isAbleToUse = isAbleToUse;
        this.startDateZone = ZonedDateTime.parse(discountEntity.getStartDate()).withZoneSameInstant(ZoneId.systemDefault());
        this.endDateZone = ZonedDateTime.parse(discountEntity.getEndDate()).withZoneSameInstant(ZoneId.systemDefault());
    }
}
