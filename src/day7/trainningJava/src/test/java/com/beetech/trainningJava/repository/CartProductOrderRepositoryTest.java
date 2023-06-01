package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.OrderEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(value = true) // để lưu dữ liệu vào database thật
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CartProductOrderRepositoryTest {
    @Autowired
    private CartProductOrderRepository cartProductOrderRepository;

    @Test
    void findAllByCartId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OrderEntity> orderEntityPage = cartProductOrderRepository.findAllByCartId(11, pageable);

        orderEntityPage.forEach(orderEntity -> {
            System.out.println(orderEntity.getId());
            orderEntity.getCartProductOrderEntities().forEach(cartProductOrderEntity -> {
                System.out.println(cartProductOrderEntity.getCartProduct().getProduct().getName());
            });
        });
    }
}