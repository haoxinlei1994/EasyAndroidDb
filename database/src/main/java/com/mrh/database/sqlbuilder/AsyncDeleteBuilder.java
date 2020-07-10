package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.AsyncDao;
import com.mrh.database.listener.DBListener;

/**
 * 异步删除构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class AsyncDeleteBuilder<T> extends AsyncSqlBuilder<T> {

    public AsyncDeleteBuilder(AsyncDao<T> AsyncDao) {
        super(AsyncDao);
    }

    @Override
    public AsyncDeleteBuilder whereClause(String whereClause) {
        super.whereClause(whereClause);
        return this;
    }

    @Override
    public AsyncDeleteBuilder<T> whereArgs(String... whereArgs) {
        super.whereArgs(whereArgs);
        return this;
    }

    @Override
    public AsyncDeleteBuilder<T> singleResultListener(DBListener<T> listener) {
        super.singleResultListener(listener);
        return this;
    }

    public void delete() {
        mAsyncDao.delete(mWhereClause, mWhereArgs, mSingleResultListener);
    }

    public void deleteAll() {
        mAsyncDao.deleteAll();
    }
}
