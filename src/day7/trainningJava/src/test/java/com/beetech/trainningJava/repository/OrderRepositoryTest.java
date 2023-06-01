package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CartProductOrderEntity;
import com.beetech.trainningJava.entity.OrderEntity;
import com.beetech.trainningJava.enums.PaymentStatus;
import com.beetech.trainningJava.model.OrderEntityDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(value = false) // để lưu dữ liệu vào database thật
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Test
    void findOrderEntityById() {
        Optional<OrderEntity> orderEntity = orderRepository.findOrderEntityById(29);
//        OrderEntity orderEntity1 = orderRepository.findById(29).get();
    }

    @Test
    void findAllByPaymentStatusAndCartId() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<OrderEntity> orderEntityDtoPage = orderRepository.findAllByPaymentStatusAndCartId(PaymentStatus.PENDING,11, pageable);
        orderEntityDtoPage.forEach(orderEntity -> {
            System.out.println(orderEntity.getClass().getSimpleName());
            orderEntity.getCartProductOrderEntities().forEach(cartProductOrderEntity -> {
                System.out.println(cartProductOrderEntity.getCartProduct().getProduct().getName());
            });
        });
        System.out.println(orderEntityDtoPage.getTotalPages());
    }

    @Test
    void findAllByCartId() {
        Pageable pageable = PageRequest.of(0, 2);
        Page<OrderEntity> orderEntityDtoPage = orderRepository.findAllByCartId(11, pageable);
        orderEntityDtoPage.forEach(orderEntity -> {
            System.out.println(orderEntity.getClass().getSimpleName());
            orderEntity.getCartProductOrderEntities().forEach(cartProductOrderEntity -> {
                System.out.println(cartProductOrderEntity.getCartProduct().getProduct().getName());
            });
        });
        System.out.println(orderEntityDtoPage.getTotalPages());
    }

    @Test
    void findAllCartProductOrderByCartId() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<CartProductOrderEntity> cartProductOrderEntities = orderRepository.findAllCartProductOrderByCartId(11, pageable);
        cartProductOrderEntities.forEach(cartProductOrderEntity -> {
            System.out.println(cartProductOrderEntity.getCartProduct().getProduct().getName());
        });

    }
}