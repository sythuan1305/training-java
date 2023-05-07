package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductWithImageUrl {
    Integer id;
    String name;
    BigDecimal price;
    Integer quantity;
    String ImageUrl;
}
