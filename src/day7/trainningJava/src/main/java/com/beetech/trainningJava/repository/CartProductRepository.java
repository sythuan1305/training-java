package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Integer> {
    @EntityGraph(attributePaths = {"product", "product.productImageurlEntities"}, type = EntityGraph.EntityGraphType.FETCH)
    List<CartProductEntity> findAllByCartIdAndIsBought(Integer cartId, boolean bought);

    CartProductEntity findByCartIdAndProductIdAndIsBought(Integer cartId, Integer productId, boolean bought);
}