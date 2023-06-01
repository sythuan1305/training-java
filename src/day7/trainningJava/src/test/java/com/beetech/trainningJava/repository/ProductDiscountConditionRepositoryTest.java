package com.beetech.trainningJava.repository;

import com.beetech.trainningJava.entity.ProductDiscountConditionEntity;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // chỉ dùng cho test repository mà không dùng service, controller
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(false) // để lưu dữ liệu vào database thật
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductDiscountConditionRepositoryTest {

    @Autowired
    private ProductDiscountConditionRepository productDiscountConditionRepository;
    @Test
    void findAllByProductIdIn() {
//        List<Integer>  productIds = List.of(1,2,104,105);
//        List<ProductDiscountConditionEntity> productDiscountConditionEntityList = productDiscountConditionRepository.findAllByProductIdIn(productIds);
//
//        productDiscountConditionEntityList.forEach(productDiscountConditionEntity -> {
//            System.out.println(productDiscountConditionEntity.getProduct().getId());
////            System.out.println(productDiscountConditionEntity.getProduct().getName());
////            System.out.println(productDiscountConditionEntity.getProduct().getPrice());
////            System.out.println(productDiscountConditionEntity.getProduct().getTestColumn());
//        });
//
//        Map<Integer, ProductDiscountConditionEntity> productDiscountConditionEntityMap = productDiscountConditionEntityList.stream()
//                .collect(Collectors.toMap(productDiscountConditionEntity -> productDiscountConditionEntity.getProduct().getId(), productDiscountConditionEntity -> productDiscountConditionEntity));

        Map<Integer, List<ProductDiscountConditionEntity>> productDiscountConditionEntityMap1 = productDiscountConditionRepository.findAllByDiscountIdInMap(List.of(1,2));

        productDiscountConditionEntityMap1.forEach((integer, productDiscountConditionEntities) -> {
            System.out.println(integer);
            productDiscountConditionEntities.forEach(productDiscountConditionEntity -> {
                System.out.println(productDiscountConditionEntity.getDiscount().getId());
                System.out.println(productDiscountConditionEntity.getDiscount().getDiscountType());
                System.out.println(productDiscountConditionEntity.getDiscount().getDiscountValue());
                System.out.println(productDiscountConditionEntity.getProduct().getId());
                System.out.println(productDiscountConditionEntity.getProduct().getName());
                System.out.println(productDiscountConditionEntity.getProduct().getPrice());
            });
        });

    }
}