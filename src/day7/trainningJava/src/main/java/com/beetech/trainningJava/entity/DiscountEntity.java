package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.enums.DiscountType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discount")
public class DiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "discount_type", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal discountValue;

    @Column(name = "max_uses", nullable = false)
    private Integer maxUses;

    @Column(name = "max_uses_per_customer", nullable = false)
    private Integer maxUsesPerCustomer;

    @Column(name = "minimum_order_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount;

    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Column(name = "description", length = 100)
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "discount")
    private Set<ProductDiscountConditionEntity> productDiscountConditions = new LinkedHashSet<>();

    public DiscountEntity(DiscountEntity discountEntity) {
        this(discountEntity.getId(),
                discountEntity.getCode(),
                discountEntity.getDiscountType(),
                discountEntity.getDiscountValue(),
                discountEntity.getMaxUses(),
                discountEntity.getMaxUsesPerCustomer(),
                discountEntity.getMinimumOrderAmount(),
                discountEntity.getStartDate(),
                discountEntity.getEndDate(),
                discountEntity.getDescription(),
                discountEntity.getProductDiscountConditions());
    }
}