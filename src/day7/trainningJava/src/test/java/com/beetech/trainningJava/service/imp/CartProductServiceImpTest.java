package com.beetech.trainningJava.service.imp;

import com.beetech.trainningJava.entity.CartProductEntity;
import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.model.ConditionModel;
import com.beetech.trainningJava.repository.CartProductRepository;
import com.beetech.trainningJava.repository.ConditionRepository;
import com.beetech.trainningJava.service.ICSVService;
import com.beetech.trainningJava.service.ICartProductService;
import com.beetech.trainningJava.service.ICartService;
import com.beetech.trainningJava.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

//@DataJpaTest
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //su dung database thuc
//@Rollback(false)
class CartProductServiceImpTest {

    @Autowired
    ICSVService csvService;

    @Autowired
    ICartProductService cartProductService;

    @Autowired
    IProductService productService;

    @Autowired
    ICartService cartService;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    ConditionRepository conditionRepository;

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void save( boolean isException) {
        // kh??i ta?o ???i t???ng ??n ha?ng
        CartProductEntity cartProductEntity = new CartProductEntity();
        ProductEntity productEntity = productService.getOne(1);
        cartProductEntity.setCart(cartService.getOne(1));
        cartProductEntity.setProduct(productEntity);
        cartProductEntity.setQuantity(10);
        cartProductEntity.setPrice(BigDecimal.ONE);
        if (isException) {
            throw new RuntimeException("Test Exception");
        }
        // l?u ???i t???ng ??n ha?ng va?o database
        cartProductRepository.save(cartProductEntity);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void MinusQuantity() {
        // l??y ???i t???ng ??n ha?ng ?a? t??n ta?i v??i id = 1
        CartProductEntity cartProductEntity1 = cartProductRepository.getReferenceById(1);

        // print CartProductEntity1
        System.out.println("CartProductEntity1: " + cartProductEntity1.getQuantity());
        // tr?? s?? l???ng
        cartProductEntity1.setQuantity(cartProductEntity1.getQuantity() - 1);
        System.out.println("CartProductEntity1: " + cartProductEntity1.getQuantity());

    }


    @Test
    @Transactional(propagation = Propagation.REQUIRED)
    void TestCase1() {
        MinusQuantity();
        save(true);
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    void TestCase2() throws Exception {
        save(false);
        MinusQuantity();
        throw new Exception("Test Exception");
    }

    @Test
    void TestCase3() throws Exception {
        TestCase2();
    }

    @Test
    void TestCurrentTime() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    void TestSet() {
        Set<ConditionModel> conditionModel = new HashSet<>();

        ConditionModel conditionModel1 = new ConditionModel();
        conditionModel1.setId("1");
        conditionModel1.setEnoughCondition(true);
        conditionModel1.setProduct(productService.getOne(1));
        conditionModel1.setConditionEntity(conditionRepository.getReferenceById(1));

        ConditionModel conditionModel2 = new ConditionModel();
        conditionModel2.setId("1");
        conditionModel2.setEnoughCondition(true);
        conditionModel2.setProduct(productService.getOne(1));
        conditionModel2.setConditionEntity(conditionRepository.getReferenceById(1));

        conditionModel.add(conditionModel1);
        conditionModel.add(conditionModel2);

        System.out.println(conditionModel.size());

    }

    @Test
    void TestZoneDateTime()
    {
        ZoneId vietnamZone = ZoneId.of("Asia/Ho_Chi_Minh"); // M?i gi? Vi?t Nam
        ZoneId newYorkZone = ZoneId.of("America/New_York"); // M?i gi? New York

        ZonedDateTime endDateInNewYork = ZonedDateTime.parse("2023-04-05T10:48:38.748250700+07:00[Asia/Bangkok]");// Chuy?n sang m?i gi? Vi?t Nam
//                .withZoneSameInstant(newYorkZone)
                ; // Chuy?n sang m?i gi? New York
        System.out.println(endDateInNewYork.getZone().toString());

        endDateInNewYork.withZoneSameInstant(newYorkZone);
        System.out.println("Th?i gian k?t th?c ?: " + endDateInNewYork.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));


        // get current time
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now.toString());
    }

    @Test
    void TestSaveCSVFile() throws IOException {
        Path path = Paths.get("E:\\Desktop\\testCsv.csv");
        String name = "testCsv.csv";
        String originalFileName = "testCsv.csv";
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name, originalFileName, contentType, content);

        csvService.csvtoListObject(result);

    }
}