package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductImageurlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageurlRepository extends JpaRepository<ProductImageurlEntity, Integer> {
    List<ProductImageurlEntity> findAllByProductId(Integer productId);

    List<ProductImageurlEntity> findAllByIdIn(List<Integer> ids);

}