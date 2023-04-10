package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductOrderRepository extends JpaRepository<CartProductOrderEntity, Integer> {
    List<CartProductOrderEntity> findAllByOrderId(Integer orderId);
}