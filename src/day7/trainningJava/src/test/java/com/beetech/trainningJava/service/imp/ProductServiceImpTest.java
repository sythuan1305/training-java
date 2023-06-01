package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ProductServiceImpTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImp productService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void TestGetOne() {
        // given
        ProductEntity productEntity = ProductEntity.builder()
                .id(1)
                .name("name")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();
        given(productService.getProductEntityById(productEntity.getId())).willReturn(productEntity);

        // when
        ProductEntity productEntity2 = productService.getProductEntityById(productEntity.getId());

        // then
        assertEquals(productEntity.getId(), productEntity2.getId(), "Id is not equal");
        assertEquals(productEntity.getName(), productEntity2.getName(), "Name is not equal");
        assertEquals(productEntity.getPrice(), productEntity2.getPrice(), "Price is not equal");
        assertEquals(productEntity.getQuantity(), productEntity2.getQuantity(), "Quantity is not equal");
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testGetProductEntityById() {
        // given
        ProductEntity productEntity = ProductEntity.builder()
                .id(1)
                .name("name")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();
        given(productService.getProductEntityById(productEntity.getId())).willReturn(productEntity);

        // when
        ProductEntity productEntity2 = productService.getProductEntityById(productEntity.getId());

        // then
        assertEquals(productEntity.getId(), productEntity2.getId(), "Id is not equal");
        assertEquals(productEntity.getName(), productEntity2.getName(), "Name is not equal");
        assertEquals(productEntity.getPrice(), productEntity2.getPrice(), "Price is not equal");
        assertEquals(productEntity.getQuantity(), productEntity2.getQuantity(), "Quantity is not equal");
    }

}