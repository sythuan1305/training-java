package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
/**
 * Class này bao gồm thông tin của sản phẩm và danh sách ảnh của sản phẩm đó
 */
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
