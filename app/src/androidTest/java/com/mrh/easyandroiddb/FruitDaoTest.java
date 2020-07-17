package com.mrh.easyandroiddb;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mrh.database.crud.SyncSQLiteDb;
import com.mrh.easyandroiddb.domains.Fruit;
import com.mrh.easyandroiddb.domains.FruitDao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by haoxinlei on 2020/7/17.
 */
@RunWith(AndroidJUnit4.class)
public class FruitDaoTest {

    @Test
    public void testInsert() {
        insert();
        Fruit fruit = SyncSQLiteDb.queryFrom(FruitDao.class)
                .whereClause("name=?")
                .whereArgs("apple")
                .queryOne();
        Assert.assertEquals(fruit.name, "apple");
        testDelete();
    }

    @Test
    public void testUpdate() {
        insert();
        Fruit apple = Fruit.buildApple();
        apple.color = "green";
        SyncSQLiteDb.updateFrom(FruitDao.class)
                .whereClause("name=?")
                .whereArgs("apple")
                .update(apple);
        Fruit fruit = SyncSQLiteDb.queryFrom(FruitDao.class)
                .whereClause("name=?")
                .whereArgs("apple")
                .queryOne();
        Assert.assertEquals(fruit.color, "green");
        testDelete();
    }

    @Test
    public void testQuery() {
        insert();
        Fruit apple = SyncSQLiteDb.queryFrom(FruitDao.class)
                .queryOne();
        Assert.assertEquals(apple.color, "red");
        Assert.assertEquals(apple.name, "apple");
        testDelete();
    }

    @Test
    public void testDelete() {
        insert();
        SyncSQLiteDb.deleteFrom(FruitDao.class)
                .whereClause("name=?")
                .whereArgs("apple")
                .delete();
        Fruit apple = SyncSQLiteDb.queryFrom(FruitDao.class)
                .queryOne();
        Assert.assertNull(apple);
    }

    private void insert() {
        SyncSQLiteDb.insertFrom(FruitDao.class)
                .insert(Fruit.buildApple());
    }
}
