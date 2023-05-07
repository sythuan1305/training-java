package com.beetech.trainningJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TrainningJavaApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TrainningJavaApplication.class, args);
    }
}
