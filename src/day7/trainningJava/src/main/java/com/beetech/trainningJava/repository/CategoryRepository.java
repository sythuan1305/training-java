package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    @EntityGraph(attributePaths = {"productEntities"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<CategoryEntity> findAll(Pageable pageable);

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findByName(String name);

    @EntityGraph(attributePaths = {"productEntities"}, type = EntityGraph.EntityGraphType.FETCH)
    Page<CategoryEntity> findByName(String name, Pageable pageable);

    boolean existsByName(String name);

    @Query(value = "select c1_0.id,c1_0.name,p1_0.id,p1_0.name,p1_0.price,p1_0.default_image_url,p1_0.quantity,p1_0.created_at,p1_0.sold_quantity " +
            "from category c1_0 " +
            "inner join product p1_0 on c1_0.id=p1_0.category_id " +
            "where c1_0.name=?1"
            , nativeQuery = true)
    Page<Object[]> findProductByCategoryName(String name, Pageable pageable);

//    default ProductsByCategoryModel findProductsInCategoryByCategoryName(String name, Pageable pageable) {
//        Page<Object[]> page = findProductByCategoryName(name, pageable);
//        List<Object[]> content = page.getContent();
//        List<ProductInCategoryModel> productInCategoryModels = content.stream().map(objects -> new ProductInCategoryModel(
//                (Integer) objects[2],
//                (String) objects[3],
//                BigDecimal.valueOf(Double.parseDouble(objects[4].toString())),
//                (String) objects[5]
//        )).toList();
//        return new ProductsByCategoryModel(
//                (Integer) content.get(0)[0],
//                content.get(0)[1].toString(),
//                productInCategoryModels
//        );
//    }

    @Query(value = "select p1_0, c1_0 " +
            "from ProductEntity p1_0 " +
            "inner join CategoryEntity c1_0 on c1_0.id=p1_0.category.id " +
            "where c1_0.name=?1")
    Page<ProductEntity> findProductsByCategoryName(String name, Pageable pageable);

}