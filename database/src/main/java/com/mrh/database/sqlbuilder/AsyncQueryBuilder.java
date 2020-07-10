package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.AsyncDao;
import com.mrh.database.listener.DBListener;

import java.util.List;

/**
 * 异步查询构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class AsyncQueryBuilder<T> extends AsyncSqlBuilder<T> {

    /**
     * 查询多条数据的回调监听，类型为 List<T>
     */
    protected DBListener<List<T>> mMultipleResultListener;
    protected String mOrder;

    public AsyncQueryBuilder(AsyncDao<T> TAsyncDao) {
        super(TAsyncDao);
    }

    @Override
    public AsyncQueryBuilder whereArgs(String... whereArgs) {
        super.whereArgs(whereArgs);
        return this;
    }

    @Override
    public AsyncQueryBuilder<T> whereClause(String whereClause) {
        super.whereClause(whereClause);
        return this;
    }

    public AsyncQueryBuilder order(String order) {
        mOrder = order;
        return this;
    }

    @Override
    public AsyncQueryBuilder<T> singleResultListener(DBListener<T> listener) {
        super.singleResultListener(listener);
        return this;
    }

    public AsyncQueryBuilder<T> multipleResultListener(DBListener<List<T>> listener) {
        mMultipleResultListener = listener;
        return this;
    }

    public void queryOne() {
        mAsyncDao.queryOne(mWhereClause, mWhereArgs, mSingleResultListener);
    }

    public void query() {
        mAsyncDao.query(mWhereClause, mWhereArgs, mOrder, mMultipleResultListener);
    }

    public void queryAll() {
        mAsyncDao.queryAll(mMultipleResultListener);
    }
}
