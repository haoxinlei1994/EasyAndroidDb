package com.mrh.easyandroiddb.domains;

import androidx.annotation.NonNull;

/**
 * Created by haoxinlei on 2020/7/7.
 */
public class Person {
    public String name;
    public int age;
    public String hobby;
    public int weight;

    public static Person buildTom() {
        Person tom = new Person();
        tom.name = "tom";
        tom.age = 25;
        tom.hobby = "basketball";
        tom.weight = 100;
        return tom;
    }

    public static Person buildJerry() {
        Person jerry = new Person();
        jerry.name = "jerry";
        jerry.age = 23;
        jerry.hobby = "football";
        jerry.weight = 98;
        return jerry;
    }

    @NonNull
    @Override
    public String toString() {
        return "name: " + name + " age: " + age + " hobby: " + hobby + " weight: " + weight;
    }
}
