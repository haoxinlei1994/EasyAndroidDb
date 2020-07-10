package com.mrh.database.helper;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(assetManager.open(file)));
            StringBuilder sql = new StringBuilder();
            String text = "";
            while ((text = reader.readLine()) != null) {
                sql.append(text);
            }
            sqLiteDatabase.execSQL(sql.toString());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
