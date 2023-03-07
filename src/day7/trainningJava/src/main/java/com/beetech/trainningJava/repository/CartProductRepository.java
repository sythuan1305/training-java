package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartProductRepository extends JpaRepository<CartProductEntity, Integer> {
}