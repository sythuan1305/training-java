package com.beetech.trainningJava.model.mappper;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.model.CategoryEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryEntityDtoMapper implements Function<CategoryEntity, CategoryEntityDto> {
    private final ProductEntityDtoMapper productEntityDtoMapper;

    @Override
    public CategoryEntityDto apply(CategoryEntity categoryEntity) {
        return new CategoryEntityDto(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getProductEntities()
                        .stream()
                        .map(productEntityDtoMapper)
                        .collect(Collectors.toSet()));
    }
}
