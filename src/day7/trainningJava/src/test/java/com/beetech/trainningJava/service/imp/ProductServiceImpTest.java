package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.repository.ProductRepository;
import com.beetech.trainningJava.service.IProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private IProductService productService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void TestSaveAndGetOne() {
        // given
        ProductEntity productEntity = ProductEntity.builder()
                .id(1)
                .name("name")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();
        given(productRepository.save(productEntity)).willReturn(productEntity);
        given(productService.getOne(productEntity.getId())).willReturn(productEntity);

        // when
        ProductEntity productEntitySaveWithService = productService.save(productEntity);
        ProductEntity productEntity2 = productService.getOne(productEntitySaveWithService.getId());

        // then
        assertEquals(productEntity.getId(), productEntity2.getId(), "Id is not equal");
    }

    @AfterEach
    void tearDown() {

    }
}