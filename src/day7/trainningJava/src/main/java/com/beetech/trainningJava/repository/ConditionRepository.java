package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionRepository extends JpaRepository<ConditionEntity, Integer> {
}