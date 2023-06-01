package com.beetech.trainningJava.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(value = true) // để lưu dữ liệu vào database thật
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartProductRepositoryTest {

    @Autowired
    private CartProductRepository cartProductRepository;

    @Test
    void existsByCartIdAndProductIdAndIsBought() {
        System.out.println("cartProductRepository.existsByCartIdAndProductIdAndIsBought(1L, 1L, false) " + cartProductRepository
                .existsByCartIdAndProductIdAndIsBought(10, 160, false));
    }

    @Test
    void findByCartIdAndProductIdAndIsBought() {
        System.out.println("cartProductRepository.findByCartIdAndProductIdAndIsBought(1L, 1L, false) " + cartProductRepository
                .findByCartIdAndProductIdAndIsBought(10, 160, false));
    }
}