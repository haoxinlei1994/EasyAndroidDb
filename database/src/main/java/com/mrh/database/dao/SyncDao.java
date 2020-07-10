package com.mrh.database.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mrh.database.EasySQLiteHelper;
import com.mrh.database.sqlbuilder.SyncDeleteBuilder;
import com.mrh.database.sqlbuilder.SyncQueryBuilder;
import com.mrh.database.sqlbuilder.SyncUpdateBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步数据库表操作类
 * Created by haoxinlei on 2020/7/7.
 *
 * @param <T> 表对应的数据对象类型
 */
public abstract class SyncDao<T> implements ORMTranslator<T> {
    /**
     * 表名
     */
    private String mTableName;
    /**
     * 可读数据库
     */
    private SQLiteDatabase mReadableDatabase;
    /**
     * 可写数据库
     */
    private SQLiteDatabase mWritableDatabase;
    /**
     * 修改数据库冲突策略
     */
    private int mConflictPolicy = SQLiteDatabase.CONFLICT_REPLACE;

    public SyncDao(String tableName) {
        mTableName = tableName;
        mReadableDatabase = EasySQLiteHelper.getInstance().getReadableDatabase();
        mWritableDatabase = EasySQLiteHelper.getInstance().getWritableDatabase();
    }

    /**
     * 插入多条数据
     *
     * @param list
     */
    public void insert(List<T> list) {
        if (list != null && list.size() > 0) {
            try {
                mWritableDatabase.beginTransaction();
                for (int i = 0; i < list.size(); i++) {
                    insert(list.get(i));
                }
                mWritableDatabase.setTransactionSuccessful();
            } finally {
                mWritableDatabase.endTransaction();
            }
        }
    }

    /**
     * 插入一条数据
     *
     * @param obj
     */
    public void insert(T obj) {
        if (obj != null) {
            mWritableDatabase.insertWithOnConflict(mTableName, null, convertObject(obj), mConflictPolicy);
        }
    }

    /**
     * 删除数据
     *
     * @param whereClause
     * @param whereArgs
     */
    public void delete(String whereClause, String[] whereArgs) {
        mWritableDatabase.delete(mTableName, whereClause, whereArgs);
    }

    /**
     * 删除表下的所有数据
     */
    public void deleteAll() {
        mWritableDatabase.delete(mTableName, null, null);
    }

    /**
     * 通过建造者形式的删除数据方法，语义更接近sql
     * @return
     */
    public SyncDeleteBuilder<T> newDeleter() {
        return new SyncDeleteBuilder<>(this);
    }

    /**
     * 更新数据
     *
     * @param obj
     * @param whereClause
     * @param whereArgs
     */
    public void update(T obj, String whereClause, String[] whereArgs) {
        if (obj != null) {
            mWritableDatabase.updateWithOnConflict(mTableName, convertObject(obj), whereClause, whereArgs, mConflictPolicy);
        }
    }

    /**
     * 通过建造者形式更新数据方法，语义更接近sql
     * @return
     */
    public SyncUpdateBuilder<T> newUpdater() {
        return new SyncUpdateBuilder<>(this);
    }

    /**
     * 查询一条数据
     *
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public T queryOne(String whereClause, String[] whereArgs) {
        List<T> list = query(whereClause, whereArgs, null);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询一个表下的所有数据
     *
     * @return
     */
    public List<T> queryAll() {
        return query(null, null, null);
    }

    /**
     * 按条件查询数据
     *
     * @param whereClause
     * @param whereArgs
     * @param orderBy
     * @return
     */
    public List<T> query(String whereClause, String[] whereArgs, String orderBy) {
        Cursor cursor = mReadableDatabase.query(mTableName, null, whereClause, whereArgs, null, null, orderBy);
        List<T> result = new ArrayList<>();
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    T obj = parseResult(cursor);
                    if (obj != null) {
                        result.add(obj);
                    }
                }
            } finally {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 通过建造者形式查询，语义更接近sql
     * @return
     */
    public SyncQueryBuilder<T> newQuery() {
        return new SyncQueryBuilder<>(this);
    }
}
