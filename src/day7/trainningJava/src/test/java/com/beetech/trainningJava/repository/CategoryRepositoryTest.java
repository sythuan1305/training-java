package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.time.ZonedDateTime;
import java.util.List;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(value = false) // để lưu dữ liệu vào database thật
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void TestSave() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("zonedDateTime " + zonedDateTime);
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name("name")
                .build();
        CategoryEntity categoryEntity1 = categoryRepository.save(categoryEntity);

        // given
        // when
        // then
    }

    @Test
    void TestFindAll() {
//        // when
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        Page<CategoryEntity> categoryEntityList = categoryRepository.findAll(pageRequest);
//
//        // then
//        categoryEntityList.forEach(categoryEntity -> {
//            System.out.println("categoryEntity.getProductEntities().size() " + categoryEntity.getProductEntities().size());
//            categoryEntity.getProductEntities().forEach(productEntity -> {
//                System.out.println("productEntity.getProductImageurlEntities().size() " + productEntity.getProductImageurlEntities().size());
//                productEntity.getProductImageurlEntities().forEach(productImageurlEntity -> {
//                    System.out.println("productImageurlEntity.getImageurl() " + productImageurlEntity.getImageUrl());
//                });
//            });
//        });

        //-----------
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        System.out.println("categoryEntityList.size() " + categoryEntityList.size());
        categoryEntityList.forEach(categoryEntity ->
                System.out.println("categoryEntity.getName() " + categoryEntity.getName())
        );
    }

    @Test
    void TestFindByName() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<CategoryEntity> categoryEntityList = categoryRepository.findByName("áo polo", pageRequest);

        System.out.println("categoryEntityList.getTotalElements() " + categoryEntityList.getTotalElements());
        System.out.println("categoryEntityList.getTotalPages() " + categoryEntityList.getTotalPages());
        System.out.println("categoryEntityList.getContent().size() " + categoryEntityList.getContent().size());
        System.out.println("categoryEntityList.getNumber() " + categoryEntityList.getNumber());
        categoryEntityList.forEach(categoryEntity -> {
            System.out.println("categoryEntity.getProductEntities().size() " + categoryEntity.getProductEntities().size());
        });
    }

    @Test
    void testFindProductsByCategory() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("name"));
        Page<Object[]> page = categoryRepository.findProductByCategoryName("áo polo", pageRequest);
        page.forEach(
                product -> {
                    for (Object o : product) {
                        System.out.println(o);
                    }
                }
        );

        System.out.println(page.getTotalPages());
    }

    @Test
    void TestFindByName123() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductEntity> productEntities = categoryRepository.findProductsByCategoryName("áo polo", pageRequest);

        productEntities.getContent().forEach(
                product -> {
                    System.out.println(product.getId());
                    System.out.println(product.getName());
                    System.out.println(product.getCategory().getName());
                }
        );
//        productEntities.getContent().forEach(
//                product -> {
//                    System.out.println(product.getId());
//                    System.out.println(product.getName());
//                    System.out.println(product.getProductEntities().size());
//                }
//        );

        System.out.println(productEntities.getTotalPages());

    }


}