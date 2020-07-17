package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.SyncDao;

import java.util.List;

/**
 * 同步插入构造类
 * Created by haoxinlei on 2020/7/17.
 */
public class SyncInsertBuilder<T> extends SyncSqlBuilder<T> {

    public SyncInsertBuilder(SyncDao<T> syncDao) {
        super(syncDao);
    }

    public void insert(T t) {
        mSyncDao.insert(t);
    }

    public void insert(List<T> list) {
        mSyncDao.insert(list);
    }
}
