package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInforModel extends ProductEntity {
    private List<String> images;

    public ProductInforModel(ProductEntity productEntity, List<String> images) {
        super(productEntity);
        this.images = images;
    }
}
