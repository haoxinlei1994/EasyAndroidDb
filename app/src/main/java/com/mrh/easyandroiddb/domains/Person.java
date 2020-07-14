package com.mrh.easyandroiddb.domains;

import androidx.annotation.NonNull;

import com.mrh.db_annotation.Column;
import com.mrh.db_annotation.Table;

/**
 * Created by haoxinlei on 2020/7/7.
 */
@Table(tableName = "person", isAsync = true)
public class Person {
    @Column("name")
    public String name;
    @Column("age")
    public int age;

    public static Person buildTom() {
        Person tom = new Person();
        tom.name = "tom";
        tom.age = 25;
        return tom;
    }

    public static Person buildJerry() {
        Person jerry = new Person();
        jerry.name = "jerry";
        jerry.age = 23;
        return jerry;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: " + name + " age: " + age;
    }
}
