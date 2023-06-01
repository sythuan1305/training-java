package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.CartEntity;
import com.beetech.trainningJava.model.CartEntityDto;

import java.util.function.Function;

public class CartEntityDtoMapper implements Function<CartEntity, CartEntityDto> {
    @Override
    public CartEntityDto apply(CartEntity cartEntity) {
        return new CartEntityDto(
                cartEntity.getId(),
                cartEntity.getTotalPrice(),
                cartEntity.getTotalQuantity(),
                cartEntity.getAddress()
        );
    }
}
