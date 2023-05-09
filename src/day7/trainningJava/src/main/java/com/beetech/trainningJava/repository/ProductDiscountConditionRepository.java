package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDiscountConditionRepository extends JpaRepository<ProductDiscountConditionEntity, Integer> {
    @EntityGraph(value = "discount - condition", type = EntityGraph.EntityGraphType.FETCH)
    List<ProductDiscountConditionEntity> findAllByProductId(Integer productId);

    @EntityGraph(value = "discount - condition", type = EntityGraph.EntityGraphType.FETCH)
    List<ProductDiscountConditionEntity> findAllByProductIdIn(List<Integer> productIds);


    @EntityGraph(attributePaths = {"condition"}, type = EntityGraph.EntityGraphType.FETCH)
    List<ProductDiscountConditionEntity> findAllByDiscountId(Integer discountId);
}