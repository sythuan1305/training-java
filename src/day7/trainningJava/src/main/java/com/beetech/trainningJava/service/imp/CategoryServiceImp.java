package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInCategoryModel;
import com.beetech.trainningJava.model.ProductsByCategoryModel;
import com.beetech.trainningJava.model.mappper.ProductInCategory.ProductToProductInCategoryModelMapper;
import com.beetech.trainningJava.repository.CategoryRepository;
import com.beetech.trainningJava.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CategoryServiceImp implements ICategoryService {
    private final CategoryRepository categoryRepository;

    private final ProductToProductInCategoryModelMapper productInCategoryMapper;

    @Override
    public PageModel<ProductsByCategoryModel> findAllProductByCategoryName(
            String name,
            Pageable pageable) {
        if (name == null || name.isEmpty())
            throw new RuntimeException("name is null or empty");

        Page<ProductEntity> page = categoryRepository.findProductsByCategoryName(name, pageable);
        if (page.getContent().isEmpty())
            throw new RuntimeException("categoryEntities is empty");

        List<ProductEntity> content = page.getContent();
        List<ProductInCategoryModel> productInCategoryModels = content.stream().map(productInCategoryMapper).toList();
        return new PageModel<>(List.of(
                new ProductsByCategoryModel(
                        content.get(0).getCategory().getId(),
                        content.get(0).getCategory().getName(),
                        productInCategoryModels)),
                page.getNumber(),
                page.getTotalPages());
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }
}
