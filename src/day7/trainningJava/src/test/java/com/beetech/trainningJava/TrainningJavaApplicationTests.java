package com.beetech.trainningJava;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.regex.Matcher;

@SpringBootTest
@AutoConfigureMockMvc
class TrainningJavaApplicationTests {
    @Test
    void contextLoads() {
		double a = 5d;
		System.out.println((long) Math.ceil(a));
    }

    @Test
    void testLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }
}
