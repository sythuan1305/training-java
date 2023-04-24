package com.beetech.trainningJava.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity này dùng để lưu thông tin giỏ hàng
 */
@Getter
@Setter
@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice = new BigDecimal("0.00");

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity = 0;

    @OneToMany(mappedBy = "cart")
    @JsonBackReference
    private Set<CartProductEntity> cartProducts = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private AccountEntity account;

    @OneToMany(mappedBy = "cart")
    @JsonBackReference
    private Set<OrderEntity> orders = new LinkedHashSet<>();
}