package com.beetech.trainningJava.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<CartProductEntity> cartProducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<ProductImageurlEntity> productImageurlEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private Set<ProductDiscountConditionEntity> productDiscountConditions = new LinkedHashSet<>();

    public ProductEntity(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = new BigDecimal(String.valueOf(price));
        this.quantity = quantity;
    }

    public ProductEntity(ProductEntity productEntity) {
        this(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getQuantity(),
                productEntity.getCartProducts(),
                productEntity.getProductImageurlEntities(),
                productEntity.getProductDiscountConditions()
        );
    }
}