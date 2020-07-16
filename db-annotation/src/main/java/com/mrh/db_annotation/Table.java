package com.mrh.db_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Table {

    /**
     * dao 对应的数据库表名
     *
     * @return
     */
    String tableName();

    /**
     * 是否为异步dao
     *
     * @return
     */
    boolean isAsync() default true;
}
