package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.AsyncDao;
import com.mrh.database.listener.DBListener;

/**
 * 异步sql构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class AsyncSqlBuilder<T> extends BaseSqlBuilder {
    /**
     * 异步dao
     */
    protected AsyncDao<T> mAsyncDao;
    /**
     * 异步操作回调监听器，类型为 T
     */
    protected DBListener<T> mSingleResultListener;

    public AsyncSqlBuilder(AsyncDao<T> AsyncDao) {
        mAsyncDao = AsyncDao;
    }

    public AsyncSqlBuilder<T> singleResultListener(DBListener<T> listener) {
        mSingleResultListener = listener;
        return this;
    }
}
