package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.SyncDao;

import java.util.List;

/**
 * 同步查询构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class SyncQueryBuilder<T> extends SyncSqlBuilder<T> {

    private String mOrder;

    public SyncQueryBuilder(SyncDao<T> syncDao) {
        super(syncDao);
    }

    @Override
    public SyncQueryBuilder<T> whereClause(String whereClause) {
        super.whereClause(whereClause);
        return this;
    }

    @Override
    public SyncQueryBuilder<T> whereArgs(String... whereArgs) {
        super.whereArgs(whereArgs);
        return this;
    }

    public SyncQueryBuilder<T> order(String order) {
        mOrder = order;
        return this;
    }

    public T queryOne() {
        return mSyncDao.queryOne(mWhereClause, mWhereArgs);
    }

    public List<T> query() {
        return mSyncDao.query(mWhereClause, mWhereArgs, mOrder);
    }

    public List<T> queryAll() {
        return mSyncDao.queryAll();
    }
}
