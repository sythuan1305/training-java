package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.AccountEntity;
import com.beetech.trainningJava.entity.RefreshTokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    // @EntityGraph(attributePaths = "cart", type =
    // EntityGraph.EntityGraphType.FETCH)
    AccountEntity findByRefreshTokenEntity(RefreshTokenEntity refreshTokenEntity);

    @Query(value = "SELECT * FROM account WHERE username = ?1", nativeQuery = true)
    List<AccountEntity> findAllByUsernameNative(String username);

    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByToken(String token);

}