package com.mrh.database.crud;

import com.mrh.database.dao.AsyncDao;
import com.mrh.database.sqlbuilder.AsyncDeleteBuilder;
import com.mrh.database.sqlbuilder.AsyncInsertBuilder;
import com.mrh.database.sqlbuilder.AsyncQueryBuilder;
import com.mrh.database.sqlbuilder.AsyncUpdateBuilder;

/**
 * 数据库异步crud
 * Created by haoxinlei on 2020/7/17.
 */
public class AsyncSQLiteDb extends BaseSQLiteDb {

    /**
     * 插入
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> AsyncInsertBuilder<T> insertFrom(Class<? extends AsyncDao<T>> daoClass) {
        return new AsyncInsertBuilder<T>(getDao(daoClass));
    }

    /**
     * 删除
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> AsyncDeleteBuilder<T> deleteFrom(Class<? extends AsyncDao<T>> daoClass) {
        return new AsyncDeleteBuilder<T>(getDao(daoClass));
    }

    /**
     * 更新
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> AsyncUpdateBuilder<T> updateFrom(Class<? extends AsyncDao<T>> daoClass) {
        return new AsyncUpdateBuilder<T>(getDao(daoClass));
    }

    /**
     * 查询
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    public static <T> AsyncQueryBuilder<T> queryFrom(Class<? extends AsyncDao<T>> daoClass) {
        return new AsyncQueryBuilder<T>(getDao(daoClass));
    }
}
