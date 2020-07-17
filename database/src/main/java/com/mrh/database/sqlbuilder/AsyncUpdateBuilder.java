package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.AsyncDao;
import com.mrh.database.listener.DBListener;

/**
 * 异步更新构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class AsyncUpdateBuilder<T> extends AsyncSqlBuilder<T> {

    public AsyncUpdateBuilder(AsyncDao<T> AsyncDao) {
        super(AsyncDao);
    }

    @Override
    public AsyncUpdateBuilder<T> whereClause(String whereClause) {
        super.whereClause(whereClause);
        return this;
    }

    @Override
    public AsyncUpdateBuilder<T> whereArgs(String... whereArgs) {
        super.whereArgs(whereArgs);
        return this;
    }

    @Override
    public AsyncUpdateBuilder<T> singleResultListener(DBListener<T> listener) {
        super.singleResultListener(listener);
        return this;
    }

    public void update(T obj) {
        mAsyncDao.update(obj, mWhereClause, mWhereArgs, mSingleResultListener);
    }
}
