package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.service.IProductService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // vì chưa config database giả nên sẽ dùng database thật
@Rollback(value = true) // để lưu dữ liệu vào database thật
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IProductServiceTest {
    @Autowired
    private IProductService iProductService;

    @Test
    void testGetProductEntityByProductId() {
        System.out.println("iProductService.getProductEntityByProductId(1L) " + iProductService.getProductEntityById(160));
    }
}
