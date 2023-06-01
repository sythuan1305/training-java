package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.model.CartProductEntityDto;
import com.beetech.trainningJava.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CartProductParserMapper implements Function<Map<String, Object>, CartProductEntityDto> {
    private final IProductService productService;
    private final ProductEntityDtoMapper productEntityDtoMapper;

    @Override
    public CartProductEntityDto apply(Map<String, Object> stringObjectMap) {
        return new CartProductEntityDto(
                Long.parseLong(stringObjectMap.get("id").toString()),
                Integer.parseInt(stringObjectMap.get("quantity").toString()),
                BigDecimal.valueOf(Double.parseDouble(stringObjectMap.get("price").toString())),
                productEntityDtoMapper
                        .apply(productService.getProductEntityById(
                                Integer.parseInt(stringObjectMap.get("product_id").toString()))),
                false);
    }
}
