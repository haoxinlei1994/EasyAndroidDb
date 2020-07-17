package com.mrh.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.mrh.database.listener.DBListener;

import java.util.List;

/**
 * 异步数据库操作类
 * Created by haoxinlei on 2020/7/7.
 */
public abstract class AsyncDao<T> implements ORMTranslator<T> {

    /**
     * 同步操作的dao
     */
    private SyncDao<T> mSyncDao;
    /**
     * 工作线程， 所有AsyncDao共用一个工作线程
     */
    private static HandlerThread sAsyncThread;
    /**
     * 工作线程Handler
     */
    private static Handler sWorkHandler;
    /**
     * 主线程Handler
     */
    private Handler mMainHandler = new Handler(Looper.getMainLooper());

    static {
        sAsyncThread = new HandlerThread("async-dao-thread");
        sAsyncThread.start();
        sWorkHandler = new Handler(sAsyncThread.getLooper());
    }

    public AsyncDao(String tableName) {
        mSyncDao = new SyncDao<T>(tableName) {
            @Override
            public ContentValues convertObject(T obj) {
                return AsyncDao.this.convertObject(obj);
            }

            @Override
            public T parseResult(Cursor cursor) {
                return AsyncDao.this.parseResult(cursor);
            }
        };
    }

    /**
     * 获取同步dao
     * @return
     */
    public SyncDao<T> getSyncDao() {
        return mSyncDao;
    }

    /**
     * 插入一条数据
     *
     * @param obj
     * @param listener
     */
    public void insert(final T obj, final DBListener<T> listener) {
        sWorkHandler.post(() -> {
            mSyncDao.insert(obj);
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(obj));
            }
        });
    }

    /**
     * 插入多条数据
     *
     * @param list
     * @param listener
     */
    public void insert(List<T> list, DBListener<List<T>> listener) {
        sWorkHandler.post(() -> {
            mSyncDao.insert(list);
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(list));
            }
        });
    }

    /**
     * 按条件删除数据
     *
     * @param whereClause
     * @param whereArgs
     * @param listener
     */
    public void delete(String whereClause, String[] whereArgs, DBListener<T> listener) {
        sWorkHandler.post(() -> {
            mSyncDao.delete(whereClause, whereArgs);
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(null));
            }
        });
    }

    /**
     * 删除一个表下的所有数据
     */
    public void deleteAll() {
        sWorkHandler.post(() -> {
            mSyncDao.deleteAll();
        });
    }

    /**
     * 更新数据
     *
     * @param obj
     * @param whereClause
     * @param whereArgs
     * @param listener
     */
    public void update(T obj, String whereClause, String[] whereArgs, DBListener<T> listener) {
        sWorkHandler.post(() -> {
            mSyncDao.update(obj, whereClause, whereArgs);
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(obj));
            }
        });
    }

    /**
     * 查询一条数据
     *
     * @param whereClause
     * @param whereArgs
     * @param listener
     */
    public void queryOne(String whereClause, String[] whereArgs, DBListener<T> listener) {
        sWorkHandler.post(() -> {
            T t = mSyncDao.queryOne(whereClause, whereArgs);
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(t));
            }
        });
    }

    /**
     * 查询表下的所有数据
     *
     * @param listener
     */
    public void queryAll(DBListener<List<T>> listener) {
        sWorkHandler.post(() -> {
            List<T> list = mSyncDao.queryAll();
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(list));
            }
        });
    }

    /**
     * 按条件查找数据
     *
     * @param whereClause
     * @param whereArgs
     * @param orderBy
     * @param listener
     */
    public void query(String whereClause, String[] whereArgs, String orderBy, DBListener<List<T>> listener) {
        sWorkHandler.post(() -> {
            List<T> result = mSyncDao.query(whereClause, whereArgs, orderBy);
            if (listener != null) {
                mMainHandler.post(() -> listener.onComplete(result));
            }
        });
    }
}
