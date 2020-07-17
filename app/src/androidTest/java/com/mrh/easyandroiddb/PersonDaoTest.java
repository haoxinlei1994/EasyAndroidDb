package com.mrh.easyandroiddb;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mrh.database.dao.SyncDao;
import com.mrh.easyandroiddb.domains.Person;
import com.mrh.easyandroiddb.domains.PersonDao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PersonDaoTest {
    private PersonDao mAsyncPersonDao;
    private SyncDao<Person> mSyncPersonDao;

    @Before
    public void setup() {
        mAsyncPersonDao = new PersonDao();
        mSyncPersonDao = mAsyncPersonDao.getSyncDao();
    }

    @Test
    public void testInsert() {
        mSyncPersonDao.insert(Person.buildTom());
        Assert.assertEquals(mSyncPersonDao.queryOne("name=?", new String[]{"tom"}).name, "tom");
        testDelete();
    }

    @Test
    public void testUpdate() {
        mSyncPersonDao.insert(Person.buildTom());
        Person tom = Person.buildTom();
        tom.age++;
        mSyncPersonDao.update(tom, "name=?", new String[]{"tom"});
        Assert.assertEquals(mSyncPersonDao.queryOne("name=?", new String[]{"tom"}).age, 26);
        testDelete();
    }

    @Test
    public void testQuery() {
        mSyncPersonDao.insert(Person.buildTom());
        Person person = mSyncPersonDao.queryOne("name=?", new String[]{"tom"});
        Assert.assertEquals(person.name, "tom");
        Assert.assertEquals(person.age, 25);
        testDelete();
    }

    @Test
    public void testDelete() {
        mSyncPersonDao.insert(Person.buildTom());
        mSyncPersonDao.delete("name=?", new String[]{"tom"});
        Assert.assertNull(mSyncPersonDao.queryOne("name=?", new String[]{"tom"}));
    }

}
