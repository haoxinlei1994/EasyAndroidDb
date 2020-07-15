package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.SyncDao;

/**
 * 同步删除构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class SyncDeleteBuilder<T> extends SyncSqlBuilder<T> {

    public SyncDeleteBuilder(SyncDao<T> syncDao) {
        super(syncDao);
    }

    @Override
    public SyncDeleteBuilder whereArgs(String... whereArgs) {
        super.whereArgs(whereArgs);
        return this;
    }

    @Override
    public SyncDeleteBuilder whereClause(String whereClause) {
        super.whereClause(whereClause);
        return this;
    }

    public void delete() {
        mSyncDao.delete(mWhereClause, mWhereArgs);
    }

    public void deleteAll() {
        mSyncDao.deleteAll();
    }
}
