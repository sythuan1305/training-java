package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.CartProductEntityDto;
import com.beetech.trainningJava.service.IAccountService;
import com.beetech.trainningJava.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartProductEntityDtoMapper implements Function<CartProductEntity, CartProductEntityDto> {
    private final ProductEntityDtoMapper productEntityDtoMapper;
    private final IProductService productService;
    private final IAccountService accountService;

    @Override
    public CartProductEntityDto apply(CartProductEntity cartProductEntity) {
        return new CartProductEntityDto(
                Long.parseLong(cartProductEntity.getId().toString()),
                cartProductEntity.getQuantity(),
                cartProductEntity.getPrice(),
                productEntityDtoMapper.apply(cartProductEntity.getProduct()),
                cartProductEntity.isBought()
        );
    }

    public CartProductEntityDto applyWithoutImageUrlList(CartProductEntity cartProductEntity) {
        return new CartProductEntityDto(
                Long.parseLong(cartProductEntity.getId().toString()),
                cartProductEntity.getQuantity(),
                cartProductEntity.getPrice(),
                productEntityDtoMapper.applyWithoutImageUrlList(cartProductEntity.getProduct()),
                cartProductEntity.isBought()
        );
    }

    public CartProductEntity convert(CartProductEntityDto cartProductEntityDto) {
        return new CartProductEntity(
                Integer.parseInt(cartProductEntityDto.getId().toString()),
                cartProductEntityDto.getQuantity(),
                cartProductEntityDto.getPrice(),
                accountService.getCartEntity(),
                productService.getProductEntityById(cartProductEntityDto.getProduct().getId()),
                cartProductEntityDto.isBought()
        );
    }
}
