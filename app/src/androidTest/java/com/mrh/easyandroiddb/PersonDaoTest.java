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
    }

    @Test
    public void testUpdate() {
        Person tom = Person.buildTom();
        tom.age++;
        mSyncPersonDao.newUpdater()
                .whereClause("name = ?")
                .whereArgs("tom")
                .update(tom);
        Assert.assertEquals(mSyncPersonDao.queryOne("name=?", new String[]{"tom"}).age, 26);
    }

    @Test
    public void testQuery() {
        Person person = mSyncPersonDao.newQuery()
                .whereClause("name = ?")
                .whereArgs("tom")
                .queryOne();
        Assert.assertEquals(person.name, "tom");
        Assert.assertEquals(person.age, 26);
    }

    @Test
    public void testDelete() {
        mSyncPersonDao.newDeleter()
                .whereClause("name=?")
                .whereArgs("tom")
                .delete();
        Assert.assertNull(mSyncPersonDao.queryOne("name=?", new String[]{"tom"}));
    }

}
