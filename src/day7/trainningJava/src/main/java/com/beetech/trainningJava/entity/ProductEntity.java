package com.beetech.trainningJava.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
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

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public ProductEntity(String name, BigDecimal price, Integer quantity, String imageUrl) {
        this.name = name;
        this.price = new BigDecimal(String.valueOf(price));
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }
}