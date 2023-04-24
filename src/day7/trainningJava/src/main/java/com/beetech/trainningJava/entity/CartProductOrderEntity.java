package com.beetech.trainningJava.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity này dùng để lưu thông tin của từng sản phẩm trong đơn hàng
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_product_order")
public class CartProductOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_product_id", nullable = false)
    private CartProductEntity cartProduct;

    public CartProductOrderEntity(CartProductEntity cartProduct, OrderEntity order) {
        this.order = order;
        this.cartProduct = cartProduct;
    }

}