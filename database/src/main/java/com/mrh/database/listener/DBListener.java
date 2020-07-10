package com.mrh.database.listener;

/**
 * 数据库操作回调
 * Created by haoxinlei on 2020/7/7.
 */
public interface DBListener<T> {
    /**
     * 异步操作完成回调
     * @param result
     */
    void onComplete(T result);
}
