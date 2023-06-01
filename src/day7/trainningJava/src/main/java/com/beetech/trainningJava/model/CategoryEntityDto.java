package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.model.ProductEntityDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link CategoryEntity}
 */
@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryEntityDto implements Serializable {
    Integer id;
    String name;
    Set<ProductEntityDto> productEntities;
}