package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {
    RefreshTokenEntity findByUsername(String email);
}