package com.mrh.db_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by haoxinlei on 2020/7/13.
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD})
public @interface Column {
    String value() default "";
}
