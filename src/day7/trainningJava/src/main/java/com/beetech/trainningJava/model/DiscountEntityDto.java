package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.DiscountEntity;
import com.beetech.trainningJava.enums.DiscountType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * DTO for {@link com.beetech.trainningJava.entity.DiscountEntity}
 */
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
public class DiscountEntityDto implements Serializable {
    Integer id;
    String code;
    DiscountType discountType;
    BigDecimal discountValue;
    Integer maxUses;
    Integer maxUsesPerCustomer;
    BigDecimal minimumOrderAmount;
    LocalDateTime startDate;
    LocalDateTime endDate;
    String description;
    Map<String, ConditionEntityDto> conditions;

    boolean isAbleToUse;

    public DiscountEntityDto(DiscountEntity discountEntity, Map<String, ConditionEntityDto> conditions, boolean isAbleToUse) {
        this.id = discountEntity.getId();
        this.code = discountEntity.getCode();
        this.discountType = discountEntity.getDiscountType();
        this.discountValue = discountEntity.getDiscountValue();
        this.maxUses = discountEntity.getMaxUses();
        this.maxUsesPerCustomer = discountEntity.getMaxUsesPerCustomer();
        this.minimumOrderAmount = discountEntity.getMinimumOrderAmount();
        this.startDate = discountEntity.getStartDate();
        this.endDate = discountEntity.getEndDate();
        this.description = discountEntity.getDescription();
        this.conditions = conditions;
        this.isAbleToUse = isAbleToUse;
    }
}