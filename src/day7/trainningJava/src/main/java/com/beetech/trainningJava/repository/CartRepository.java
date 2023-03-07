package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Integer> {
}