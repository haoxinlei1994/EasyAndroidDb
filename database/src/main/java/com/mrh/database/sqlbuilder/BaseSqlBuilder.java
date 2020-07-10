package com.mrh.database.sqlbuilder;

/**
 * 构建sql语句基类
 * Created by haoxinlei on 2020/7/9.
 */
public class BaseSqlBuilder {
    /**
     * 查询条件 eg："name=?"
     */
    protected String mWhereClause;
    /**
     * 查询条件中占位符的实际值  eg："tom"
     */
    protected String[] mWhereArgs;

    public BaseSqlBuilder whereClause(String whereClause) {
        mWhereClause = whereClause;
        return this;
    }

    public BaseSqlBuilder whereArgs(String... whereArgs) {
        mWhereArgs = whereArgs;
        return this;
    }
}
