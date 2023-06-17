package com.beetech.trainningJava.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entity này dùng để lưu thông tin sản phẩm
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "productEntity",
                attributeNodes = {
                        @NamedAttributeNode("productImageurlEntities"),
                        @NamedAttributeNode("category")}),
        @NamedEntityGraph(name = "productEntity.category", attributeNodes = @NamedAttributeNode("category"))
})
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<CartProductEntity> cartProducts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<ProductImageurlEntity> productImageurlEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonBackReference
    private Set<ProductDiscountConditionEntity> productDiscountConditions = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sold_quantity", nullable = false)
    private Integer soldQuantity;

    @Column(name = "default_image_url", nullable = false, length = 100)
    private String defaultImageUrl;

    @Column(name = "description", length = 100)
    private String description;

    public ProductEntity(String name, BigDecimal price, Integer quantity) {
        this.name = name;
        this.price = new BigDecimal(String.valueOf(price));
        this.quantity = quantity;
    }

    public ProductEntity(String name, BigDecimal price, Integer quantity, String testColumn) {
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
                productEntity.getProductDiscountConditions(),
                productEntity.getCategory(),
                productEntity.getCreatedAt(),
                productEntity.getSoldQuantity(),
                productEntity.getDefaultImageUrl(),
                productEntity.getDescription()
        );
    }
}