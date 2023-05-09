package com.beetech.trainningJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity này dùng để lưu các điều kiện của discount áp dụng cho từng sản phẩm
 */
@Getter
@Setter
@Entity
@Table(name = "product_discount_condition")
@NamedEntityGraphs(
        @NamedEntityGraph(
                name = "discount - condition",
                attributeNodes = {
                        @NamedAttributeNode("discount"),
                        @NamedAttributeNode("condition")
                }
        )
)
public class ProductDiscountConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "discount_id", nullable = false)
    private DiscountEntity discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condition_id")
    private ConditionEntity condition;
}