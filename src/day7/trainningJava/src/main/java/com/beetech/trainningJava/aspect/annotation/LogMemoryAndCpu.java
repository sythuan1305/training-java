package com.beetech.trainningJava.aspect.annotation;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation này dùng để annotate các hàm, class,... cần log về memory và cpu
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface LogMemoryAndCpu {
    String value() default "";
}
