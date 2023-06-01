package com.beetech.trainningJava.model;

import java.math.BigDecimal;

public record ProductInCategoryModel(
        Integer id,
        String name,
        BigDecimal price,
        String image
) {
}

