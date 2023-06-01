package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link ProductEntity}
 */
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ProductEntityDto implements Serializable {
    Integer id;
    String name;
    BigDecimal price;
    Integer quantity;
    LocalDateTime createdAt;
    Integer soldQuantity;
    String defaultImageUrl;
    List<String> images;
}