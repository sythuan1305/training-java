package com.beetech.trainningJava.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity này dùng để lưu các ảnh của sản phẩm
 */
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_imageurl")
public class ProductImageurlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image_url", length = 100, nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private ProductEntity product;
}