package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductEntityDto;
import com.beetech.trainningJava.model.ProductsByCategoryModel;
import com.beetech.trainningJava.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductEntityDtoMapper implements Function<ProductEntity, ProductEntityDto> {
    @Override
    public ProductEntityDto apply(ProductEntity productEntity) {
        return new ProductEntityDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getQuantity(),
                productEntity.getCreatedAt(),
                productEntity.getSoldQuantity(),
                Utils.Base64Image.getImageByPath(productEntity.getDefaultImageUrl()),
                productEntity.getCategory().getName(),
                productEntity.getDescription(),
                productEntity.getProductImageurlEntities()
                        .stream()
                        .map(productImageurlEntity -> Utils.Base64Image.getImageByPath(productImageurlEntity.getImageUrl()))
                        .toList());
    }

    public ProductEntityDto applyWithoutImageUrlList(ProductEntity productEntity) {
        return new ProductEntityDto(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getQuantity(),
                productEntity.getCreatedAt(),
                productEntity.getSoldQuantity(),
                Utils.Base64Image.getImageByPath(productEntity.getDefaultImageUrl()),
                productEntity.getCategory().getName(),
                productEntity.getDescription(),
                null);
    }
}
