package com.mrh.database.sqlbuilder;

import com.mrh.database.dao.SyncDao;

/**
 * 同步sql构造类
 * Created by haoxinlei on 2020/7/9.
 */
public class SyncSqlBuilder<T> extends BaseSqlBuilder {
    /**
     * 同步dao
     */
    protected SyncDao<T> mSyncDao;

    public SyncSqlBuilder(SyncDao<T> syncDao) {
        mSyncDao = syncDao;
    }
}
