package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}