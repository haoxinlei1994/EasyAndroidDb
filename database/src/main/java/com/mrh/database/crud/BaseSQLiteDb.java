package com.mrh.database.crud;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by haoxinlei on 2020/7/17.
 */
class BaseSQLiteDb {

    /**
     * 缓存使用过的dao对象
     */
    protected static Map<Class, Object> sDaoMap = new LinkedHashMap<>();

    /**
     * 根据dao的class，先去缓存中查找，找不到直接创建
     *
     * @param daoClass
     * @param <T>
     * @return
     */
    protected static <T> T getDao(Class<? extends T> daoClass) {
        T dao = (T) sDaoMap.get(daoClass);
        if (dao == null) {
            try {
                dao = daoClass.newInstance();
                sDaoMap.put(daoClass, dao);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (dao == null) {
            throw new IllegalStateException(daoClass.getSimpleName() + " init error!");
        }
        return dao;
    }
}
