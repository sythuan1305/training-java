package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link com.beetech.trainningJava.entity.CartEntity}
 */
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CartEntityDto implements Serializable {
    Integer id;
    BigDecimal totalPrice;
    Integer totalQuantity;
    String address;
}