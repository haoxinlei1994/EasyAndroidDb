package com.mrh.database.dao;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * orm 转化抽象接口
 * 完成 对象Object 与 ContentValues 的转换
 *
 * @param <T>
 */
public interface ORMTranslator<T> {

    /**
     * 对象数据存储为数据库数据
     * 将对象转化为ContentValues类型
     *
     * @param obj
     * @return
     */
    ContentValues convertObject(T obj);

    /**
     * 数据库数据解析成对象数据
     * 根据游标解析为对象数据
     *
     * @param cursor
     * @return
     */
    T parseResult(Cursor cursor);
}
