package com.mrh.database.crud;

import com.mrh.database.dao.SyncDao;
import com.mrh.database.sqlbuilder.SyncDeleteBuilder;
import com.mrh.database.sqlbuilder.SyncInsertBuilder;
import com.mrh.database.sqlbuilder.SyncQueryBuilder;
import com.mrh.database.sqlbuilder.SyncUpdateBuilder;

/**
 * 数据库同步crud
 * Created by haoxinlei on 2020/7/17.
 */
public class SyncSQLiteDb extends BaseSQLiteDb {

    /**
     * 插入
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> SyncInsertBuilder<T> insertFrom(Class<? extends SyncDao<T>> daoClass) {
        return new SyncInsertBuilder<T>(getDao(daoClass));
    }

    /**
     * 删除
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> SyncDeleteBuilder<T> deleteFrom(Class<? extends SyncDao<T>> daoClass) {
        return new SyncDeleteBuilder<T>(getDao(daoClass));
    }

    /**
     * 更新
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> SyncUpdateBuilder<T> updateFrom(Class<? extends SyncDao<T>> daoClass) {
        return new SyncUpdateBuilder<T>(getDao(daoClass));
    }

    /**
     * 查询
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> SyncQueryBuilder<T> queryFrom(Class<? extends SyncDao<T>> daoClass) {
        return new SyncQueryBuilder<T>(getDao(daoClass));
    }
}
