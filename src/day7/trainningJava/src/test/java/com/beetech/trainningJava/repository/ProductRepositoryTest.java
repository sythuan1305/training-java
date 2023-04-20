package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    ProductEntity productEntity;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Order(1)
    void TestSave() {
        // given
        productEntity = ProductEntity.builder()
//                .id(1000) // id tự tăng nên không cần set
                .name("name")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();
        // when
        ProductEntity productEntity1 = productRepository.save(productEntity); // id tự tăng
        // then
//        assertEquals(productEntity.getId(), productEntity1.getId()); wrong
        assertEquals(productEntity.getName(), productEntity1.getName());
        assertEquals(productEntity.getPrice(), productEntity1.getPrice());
        assertEquals(productEntity.getQuantity(), productEntity1.getQuantity());

        productEntity = productEntity1;
    }

    @Test
    @Order(2)
    void TestGetOne() {
        // given
        TestSave();
        // when
        ProductEntity productEntity1 = productRepository.getOne(productEntity.getId());
        // then
        assertEquals(productEntity.getId(), productEntity1.getId());
        assertEquals(productEntity.getName(), productEntity1.getName());
        assertEquals(productEntity.getPrice(), productEntity1.getPrice());
        assertEquals(productEntity.getQuantity(), productEntity1.getQuantity());
    }

    @AfterEach
    void tearDown() {
    }
}