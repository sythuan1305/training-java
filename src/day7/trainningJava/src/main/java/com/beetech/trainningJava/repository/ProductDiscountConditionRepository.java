package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDiscountConditionRepository extends JpaRepository<ProductDiscountConditionEntity, Integer> {
    List<ProductDiscountConditionEntity> findAllByProductId(Integer productId);

    List<ProductDiscountConditionEntity> findAllByDiscountId(Integer discountId);
}