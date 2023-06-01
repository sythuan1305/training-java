package com.beetech.trainningJava.model;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public record ProductsByCategoryModel(
        Integer id,
        String name,
        List<ProductInCategoryModel> products
) {
}
