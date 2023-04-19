package com.beetech.trainningJava.aspect.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    String value() default "";
}