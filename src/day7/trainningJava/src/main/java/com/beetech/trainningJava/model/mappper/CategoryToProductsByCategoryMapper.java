package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.model.ProductInCategoryModel;
import com.beetech.trainningJava.model.ProductsByCategoryModel;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CategoryToProductsByCategoryMapper implements Function<CategoryEntity, ProductsByCategoryModel> {
    @Override
    public ProductsByCategoryModel apply(CategoryEntity categoryEntity) {
        return new ProductsByCategoryModel(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getProductEntities()
                        .stream()
                        .map(productEntity -> new ProductInCategoryModel(
                                productEntity.getId(),
                                productEntity.getName(),
                                productEntity.getPrice(),
                                productEntity.getDefaultImageUrl()
                        )).collect(Collectors.toList())
        );
    }
}
