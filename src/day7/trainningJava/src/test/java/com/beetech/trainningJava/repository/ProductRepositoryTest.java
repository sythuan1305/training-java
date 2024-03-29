package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.CategoryEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.entity.ProductImageurlEntity;
import com.beetech.trainningJava.model.ProductInforModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(value = true) // để lưu dữ liệu vào database thật
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    ProductEntity productEntity;
    @Autowired
    private ProductImageurlRepository productImageurlRepository;

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
        System.out.println("productEntity1 " + productEntity1.getId());
        // then
//        assertEquals(productEntity.getId(), productEntity1.getId()); wrong
        assertEquals(productEntity.getName(), productEntity1.getName());
        assertEquals(productEntity.getPrice(), productEntity1.getPrice());
        assertEquals(productEntity.getQuantity(), productEntity1.getQuantity());

        // thêm trường (default) vào product để test
        // khong set gia tri cho truong tren (de null)
        // kiem chung = assertEquals voi truong tren
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

    @Test
    void testJoin() {
        // given
        // when
        // then

//        List<ProductWithImageUrl> productWithImageUrl = productRepository.findProductWithImageUrlById(104);
//        productWithImageUrl.forEach(
//                product -> {
//                    System.out.println(product.getId());
//                    System.out.println(product.getName());
//                    System.out.println(product.getQuantity());
//                    System.out.println(product.getImageUrl());
//                }
//        );

//        List<Object[]> productWithImageUrlNative = productRepository.findProductWithImageUrlByIdNative(104);
//        productWithImageUrlNative.forEach(
//                product -> {
//                    for (Object o : product) {
//                        System.out.println(o);
//                    }
//                }
//        );
        PageRequest pageRequest = PageRequest.of(0 < 0 ? 0 : 0, 5, Sort.by("name"));
        Page<Object[]> productsWithImageUrls = productRepository.findAllProductWithImageUrls(pageRequest);
//        productsWithImageUrls.forEach(
//                product -> {
//                    for (Object o : product) {
//                        System.out.println(o);
//                    }
//                }
//        );

        List<ProductInforModel> productInforModels = new ArrayList<>();
        ProductInforModel productInforModel = new ProductInforModel();
        ProductEntity productEntity1 = new ProductEntity();
        for (Object[] product : productsWithImageUrls.getContent()) {
            if (productEntity1.getId() == null || !product[0].equals(productEntity1.getId())) {
                productEntity1 = ProductEntity.builder()
                        .id((Integer) product[0])
                        .name((String) product[1])
                        .price(BigDecimal.valueOf(Double.parseDouble(product[2].toString())))
                        .quantity((Integer) product[3])
                        .build();
                productInforModel = new ProductInforModel(productEntity1, new ArrayList<>());
                productInforModels.add(productInforModel);
            }
            productInforModel.getImages().add((String) product[4]);
        }
        productInforModels.forEach(
                product -> {
                    System.out.println(product.getId());
                    System.out.println(product.getName());
                    System.out.println(product.getQuantity());
                    System.out.println(product.getPrice());
                    product.getImages().forEach(
                            System.out::println
                    );
                }
        );
    }

    @Test
    void testSaveList() {
        ProductEntity product = productRepository.getReferenceById(104);
        // create product imageUrl entity list
        List<ProductImageurlEntity> productImageurlEntities = new ArrayList<>();
        productImageurlEntities.add(ProductImageurlEntity.builder()
                .imageUrl("imageUrl1")
                .product(product)
                .build());
        productImageurlEntities.add(ProductImageurlEntity.builder().imageUrl("imageUrl2").product(product).build());

        productImageurlRepository.saveAll(productImageurlEntities);
    }

    @Test
    void TestFindByName() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<CategoryEntity> categoryEntities = productRepository.findProductsByCategoryName("áo polo", pageRequest);

        categoryEntities.getContent().forEach(
                categoryEntity -> {
                    System.out.println(categoryEntity.getName());
                    categoryEntity.getProductEntities().forEach(
                            productEntity -> {
                                System.out.println(productEntity.getName());
                            }
                    );
                }
        );
    }


    @Test
    void testFindByName() {
        ProductEntity product = productRepository.findByName("áo polo 1");
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getQuantity());
        System.out.println(product.getPrice());
        System.out.println(product.getCategory().getName());
    }

    @Test
    void testFindById() {
        Optional<ProductEntity> product = productRepository.findById(160);
        ProductEntity productEntity = product.get();
        System.out.println(productEntity.getId());
        System.out.println(productEntity.getName());
        System.out.println(productEntity.getQuantity());
        System.out.println(productEntity.getPrice());
        System.out.println(productEntity.getCategory().getName());
    }

    @Test
    void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<ProductEntity> productEntities = productRepository.findAll(pageRequest);
        productEntities.getContent().forEach(
                productEntity -> {
                    System.out.println(productEntity.getId());
                    System.out.println(productEntity.getName());
                    System.out.println(productEntity.getQuantity());
                    System.out.println(productEntity.getPrice());
                    productEntity.getProductImageurlEntities().forEach(
                            productImageurlEntity -> {
                                System.out.println(productImageurlEntity.getImageUrl());
                            }
                    );
                    System.out.println(productEntity.getCategory().getName());
                }
        );
        System.out.println(productEntities.getTotalElements());
        System.out.println(productEntities.getTotalPages());
        System.out.println(productEntities.getNumber());

    }

    @Test
    void testfindAllByCategoryId() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductEntity> productEntities = productRepository.findAllByCategoryId(1, pageRequest);
        productEntities.getContent().forEach(
                productEntity -> {
                    System.out.println(productEntity.getId());
                    System.out.println(productEntity.getName());
                    System.out.println(productEntity.getQuantity());
                    System.out.println(productEntity.getPrice());
                    System.out.println(productEntity.getDefaultImageUrl());
                }
        );
        System.out.println(productEntities.getTotalElements());
        System.out.println(productEntities.getTotalPages());
        System.out.println(productEntities.getNumber());
    }

    @Test
    void findProductEntityById() {
        Optional<ProductEntity> productEntity = productRepository.findProductEntityById(160);
    }
}