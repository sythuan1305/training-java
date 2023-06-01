package com.beetech.trainningJava.model.mappper.ProductInCategory;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.ProductInCategoryModel;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductToProductInCategoryModelMapper implements Function<ProductEntity, ProductInCategoryModel> {
    @Override
    public ProductInCategoryModel apply(ProductEntity productEntity) {
        return new ProductInCategoryModel(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getDefaultImageUrl()
        );
    }
}
