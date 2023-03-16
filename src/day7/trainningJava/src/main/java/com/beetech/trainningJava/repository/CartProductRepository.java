package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartProductRepository extends JpaRepository<CartProductEntity, Integer> {
    List<CartProductEntity> findAllByCartId(Integer cartId);
}