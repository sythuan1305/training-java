package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    AccountEntity findByUsername(String username);

    List<AccountEntity> findAllByUsername(String username);

    @Query(value = "SELECT * FROM account WHERE username = ?1", nativeQuery = true)
    List<AccountEntity> findAllByUsernameNative(String username);
}