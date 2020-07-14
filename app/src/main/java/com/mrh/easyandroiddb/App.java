package com.mrh.easyandroiddb;

import android.app.Application;

import com.mrh.database.EasySQLiteHelper;

/**
 * Created by haoxinlei on 2020/7/7.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new EasySQLiteHelper.Builder(this)
                .dbName("easy.db")
                .dbVersion(1)
                .build();
    }
}
