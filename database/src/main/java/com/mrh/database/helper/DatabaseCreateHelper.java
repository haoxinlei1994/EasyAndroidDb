package com.mrh.database.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.util.regex.Pattern;

/**
 * 数据库创建帮助类
 * 会读取 assets 目录下的满足 "^(create).*(.sql)$" 正则的数据库创建文件
 * Created by haoxinlei on 2020/7/7.
 */
public class DatabaseCreateHelper {

    private AssetSqlExecutor mAssetSqlExecutor = new AssetSqlExecutor();

    /**
     * 读取 create.sql 文件创建数据库表
     * @param context
     * @param sqLiteDatabase
     */
    public void create(Context context, SQLiteDatabase sqLiteDatabase) {
        try {
            AssetManager assetManager = context.getAssets();
            String[] list = assetManager.list("");
            if (list != null && list.length > 0) {
                sqLiteDatabase.beginTransaction();
                for (int i = 0; i < list.length; i++) {
                    if (Pattern.matches("^(create).*(.sql)$", list[i])) {
                        mAssetSqlExecutor.executeSql(list[i], assetManager, sqLiteDatabase);
                    }
                }
                sqLiteDatabase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }
}
