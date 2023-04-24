package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.enums.ConditionType;
import com.beetech.trainningJava.enums.LogicalOperator;
import com.beetech.trainningJava.enums.Operator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity này dùng để lưu thông tin điều kiện giảm giá
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`condition`")
public class ConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "condition_type", nullable = false, length = 19)
    @Enumerated(EnumType.STRING)
    private ConditionType conditionType;

    @Column(name = "condition_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal conditionValue;

    @Column(name = "operator", nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private Operator operator;


    @Column(name = "logical_operator", nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private LogicalOperator logicalOperator;

    @Column(name = "description", length = 100)
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "condition")
    private Set<ProductDiscountConditionEntity> productDiscountConditions = new LinkedHashSet<>();

    public ConditionEntity(ConditionEntity conditionEntity) {
        this(conditionEntity.getId(),
                conditionEntity.getConditionType(),
                conditionEntity.getConditionValue(),
                conditionEntity.getOperator(),
                conditionEntity.getLogicalOperator(),
                conditionEntity.getDescription(),
                conditionEntity.getProductDiscountConditions());
    }

}