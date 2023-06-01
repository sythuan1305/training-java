package com.beetech.trainningJava.service;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductsByCategoryModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    PageModel<ProductsByCategoryModel> findAllProductByCategoryName(String name, Pageable pageable);

    List<CategoryEntity> findAll();

}
