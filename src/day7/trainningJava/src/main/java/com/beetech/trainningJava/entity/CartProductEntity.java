package com.beetech.trainningJava.entity;

import com.beetech.trainningJava.model.CartProductInforModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Entity này dùng để lưu thông tin sản phẩm trong giỏ hàng
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_product"
//        , indexes = {@Index(name = "cart_product_index", columnList = "cart_id, product_id")}
) // OPTIMIZE INDEX
@NamedEntityGraphs({
        @NamedEntityGraph(name = "cartProductEntity.product",
                attributeNodes = {
                        @NamedAttributeNode("product")
                }),
})
public class CartProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    @JsonManagedReference
    private CartEntity cart;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonManagedReference
    private ProductEntity product;

    @Column(name = "is_bought", nullable = false)
    private boolean isBought = false;

    public CartProductEntity(CartProductEntity cartProductEntity) {
        this(cartProductEntity.getId(),
                cartProductEntity.getQuantity(),
                cartProductEntity.getPrice(),
                cartProductEntity.getCart(),
                cartProductEntity.getProduct(),
                cartProductEntity.isBought());
    }

    public CartProductEntity(CartProductInforModel cartProductInforModel) {
        this(cartProductInforModel.getId(),
                cartProductInforModel.getQuantity(),
                cartProductInforModel.getPrice(),
                cartProductInforModel.getCart(),
                cartProductInforModel.getProduct(),
                cartProductInforModel.isBought());
    }

}