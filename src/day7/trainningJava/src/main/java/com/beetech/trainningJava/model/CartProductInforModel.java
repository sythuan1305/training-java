package com.beetech.trainningJava.model;

import com.beetech.trainningJava.entity.CartProductEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartProductInforModel extends CartProductEntity {

    ProductInforModel productModel;

    public CartProductInforModel(CartProductEntity cartProductEntity, ProductInforModel productModel) {
        super(cartProductEntity);
        this.productModel = productModel;
    }
}
