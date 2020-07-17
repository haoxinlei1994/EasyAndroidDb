package com.mrh.easyandroiddb.domains;

import com.mrh.db_annotation.Column;
import com.mrh.db_annotation.Table;

/**
 * Created by haoxinlei on 2020/7/17.
 */
@Table(tableName = "fruit", isAsync = false)
public class Fruit {
    @Column
    public String name;
    @Column
    public String color;

    public static Fruit buildApple() {
        Fruit fruit = new Fruit();
        fruit.name = "apple";
        fruit.color = "red";
        return fruit;
    }
}
