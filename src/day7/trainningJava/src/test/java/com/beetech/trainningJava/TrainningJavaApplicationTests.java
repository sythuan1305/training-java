package com.beetech.trainningJava;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;

@SpringBootTest
@AutoConfigureMockMvc
class TrainningJavaApplicationTests {
    @Test
    void contextLoads() {
		double a = 5d;
		System.out.println((long) Math.ceil(a));
    }
}
