package com.beetech.trainningJava.model.mappper.ProductInCategory;

import com.beetech.trainningJava.model.ProductInCategoryModel;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Function;

@Service
public class ObjectsToProductInCategoryModelMapper implements Function<Object[], ProductInCategoryModel> {
    @Override
    public ProductInCategoryModel apply(Object[] objects) {
        return new ProductInCategoryModel(
                (Integer) objects[2],
                (String) objects[3],
                BigDecimal.valueOf(Double.parseDouble(objects[4].toString())),
                (String) objects[5]
        );
    }
}
