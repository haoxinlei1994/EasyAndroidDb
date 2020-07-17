package com.mrh.database.helper;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import com.mrh.database.sqlpaser.SqlParser;

import java.util.List;

/**
 * 执行 assets 目录下的 create.sql 或 upgrade_2.sql 文件
 * Created by haoxinlei on 2020/7/7.
 */
public class AssetSqlExecutor {

    /**
     * 执行sql
     * @param file assets 目录下的文件路径
     * @param assetManager
     * @param sqLiteDatabase
     */
    void executeSql(String file, AssetManager assetManager, SQLiteDatabase sqLiteDatabase) throws Exception {
        List<String> sqlList = SqlParser.parse(assetManager.open(file));
        for (String sql : sqlList) {
            sqLiteDatabase.execSQL(sql);
        }
    }
}
