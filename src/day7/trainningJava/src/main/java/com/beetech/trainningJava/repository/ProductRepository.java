package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.ProductWithImageUrl;
import jakarta.persistence.Entity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findByName(String name);

    boolean existsByName(String name);

    // join with product_imageurl table with ProductWithImageUrl model
    @Query(value = "SELECT new com.beetech.trainningJava.model.ProductWithImageUrl(p.id, p.name, p.price, p.quantity, pi.imageUrl) " +
            "FROM ProductEntity p " +
            "INNER JOIN ProductImageurlEntity pi " +
            "ON p.id = pi.product.id " +
            "WHERE p.id = ?1")
    List<ProductWithImageUrl> findProductWithImageUrlById(Integer id);

    // join with product_imageurl table with ProductWithImageUrl model and native query
    @Query(value = "SELECT p.id, p.name, p.price, p.quantity, pi.image_url " +
            "FROM product p " +
            "INNER JOIN product_imageurl pi " +
            "ON p.id = pi.product_id " +
            "WHERE p.id = ?1", nativeQuery = true)
    List<Object[]> findProductWithImageUrlByIdNative(Integer id);

    // join with product_imageurl table and get by page request
    @Query(value = "SELECT p.id, p.name, p.price, p.quantity, pi.imageUrl " +
            "FROM ProductEntity p " +
            "INNER JOIN ProductImageurlEntity pi " +
            "ON p.id = pi.product.id")
    Page<Object[]> findAllProductWithImageUrls(Pageable pageable);

    @EntityGraph(attributePaths = {"productImageurlEntities"}, type = EntityGraph.EntityGraphType.FETCH)
    @NonNull
    Page<ProductEntity> findAll(@NonNull Pageable pageable);

    // count all product join with product_imageurl table
    @Query(value = "SELECT COUNT(p.id) " +
            "FROM ProductEntity p " +
            "LEFT JOIN ProductImageurlEntity pi " +
            "ON p.id = pi.product.id")
    long count();
}