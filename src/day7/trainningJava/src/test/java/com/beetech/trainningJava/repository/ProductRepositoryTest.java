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
        // given
         productEntity = ProductEntity.builder()
                .id(1)
                .name("name")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();
    }

    @Test
    @Order(1)
    void TestSave() {
        // when
        productRepository.save(productEntity);
        // then
        assertNotNull(productEntity.getId(), "Id is null");
    }

    @Test
    @Order(2)
    void TestGetOne() {
        // when
        ProductEntity productEntity2 = productRepository.getOne(productEntity.getId());
        // then
        assertEquals(productEntity.getId(), productEntity2.getId(), "Id is not equal");
    }

    @AfterEach
    void tearDown() {
    }
}