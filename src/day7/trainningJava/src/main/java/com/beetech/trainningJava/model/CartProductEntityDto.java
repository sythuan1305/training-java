package com.beetech.trainningJava.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * DTO for {@link com.beetech.trainningJava.entity.CartProductEntity}
 */
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CartProductEntityDto implements Serializable {
    Long id;
    Integer quantity;
    BigDecimal price;
    ProductEntityDto product;
    boolean isBought;
}