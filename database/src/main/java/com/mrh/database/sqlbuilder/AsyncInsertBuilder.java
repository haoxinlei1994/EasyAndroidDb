package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.AsyncDao;
import com.mrh.database.listener.DBListener;

import java.util.List;

/**
 * 异步插入构造类
 * Created by haoxinlei on 2020/7/17.
 */
public class AsyncInsertBuilder<T> extends AsyncSqlBuilder<T> {

    /**
     * 查询多条数据的回调监听，类型为 List<T>
     */
    protected DBListener<List<T>> mMultipleResultListener;

    public AsyncInsertBuilder(AsyncDao<T> AsyncDao) {
        super(AsyncDao);
    }

    @Override
    public AsyncInsertBuilder<T> singleResultListener(DBListener<T> listener) {
        super.singleResultListener(listener);
        return this;
    }

    public AsyncInsertBuilder<T> multipleResultListener(DBListener<List<T>> listener) {
        mMultipleResultListener = listener;
        return this;
    }

    public void insert(T t) {
        mAsyncDao.insert(t, mSingleResultListener);
    }

    public void insert(List<T> list) {
        mAsyncDao.insert(list, mMultipleResultListener);
    }
}
