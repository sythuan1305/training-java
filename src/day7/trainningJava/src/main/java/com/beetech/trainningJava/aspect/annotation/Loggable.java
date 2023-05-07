package com.beetech.trainningJava.aspect.annotation;

import java.lang.annotation.*;

/**
 * Annotation này dùng để annotate các hàm, class,... cần log
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Loggable {
    String value() default "";
}