package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.model.CartProductInforModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entity này dùng để lưu thông tin sản phẩm trong giỏ hàng
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product")
public class CartProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonManagedReference
    private ProductEntity product;

    @OneToOne(mappedBy = "cartProduct")
    @JsonBackReference
    private CartProductOrderEntity cartProductOrdersEntity;

    @Column(name = "is_bought", nullable = false)
    private boolean isBought = false;

    public CartProductEntity(CartProductEntity cartProductEntity) {
        this(cartProductEntity.getId(),
                cartProductEntity.getQuantity(),
                cartProductEntity.getPrice(),
                cartProductEntity.getCart(),
                cartProductEntity.getProduct(),
                cartProductEntity.getCartProductOrdersEntity(),
                cartProductEntity.isBought());
    }

    public CartProductEntity(CartProductInforModel cartProductInforModel) {
        this(cartProductInforModel.getId(),
                cartProductInforModel.getQuantity(),
                cartProductInforModel.getPrice(),
                cartProductInforModel.getCart(),
                cartProductInforModel.getProduct(),
                cartProductInforModel.getCartProductOrdersEntity(),
                cartProductInforModel.isBought());
    }

}