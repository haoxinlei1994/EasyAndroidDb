package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.SyncDao;

/**
 * 同步更新构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class SyncUpdateBuilder<T> extends SyncSqlBuilder<T> {

    public SyncUpdateBuilder(SyncDao<T> syncDao) {
        super(syncDao);
    }

    @Override
    public SyncUpdateBuilder whereClause(String whereClause) {
        super.whereClause(whereClause);
        return this;
    }

    @Override
    public SyncUpdateBuilder whereArgs(String... whereArgs) {
        super.whereArgs(whereArgs);
        return this;
    }

    public void update(T obj) {
        mSyncDao.update(obj, mWhereClause, mWhereArgs);
    }
}
