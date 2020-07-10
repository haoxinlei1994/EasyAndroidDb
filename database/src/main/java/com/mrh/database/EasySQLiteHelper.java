package com.mrh.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.mrh.database.helper.DatabaseCreateHelper;
import com.mrh.database.helper.DatabaseUpgradeHelper;

/**
 * 采用单例+builder模式，需要尽早在Application中初始化
 * Created by haoxinlei on 2020/7/6.
 */
public class EasySQLiteHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static EasySQLiteHelper sInstance;

    public static EasySQLiteHelper getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("EasySQLiteHelper must init in application first!");
        }
        return sInstance;
    }

    private EasySQLiteHelper(Builder builder) {
        super(builder.context, builder.dbName, null, builder.dbVersion);
        mContext = builder.context;
        //调用后会马上创建数据库并回调 onCreate 方法
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        new DatabaseCreateHelper().create(mContext, sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        new DatabaseUpgradeHelper().upgrade(mContext, sqLiteDatabase, i, i1);
    }

    /**
     * builder
     */
    public static class Builder {
        private Context context;
        private String dbName;
        private int dbVersion;

        public Builder(Context context) {
            this.context = context;
            dbName = context.getPackageManager().getApplicationLabel(context.getApplicationInfo()) + ".db";
            dbVersion = 1;
        }

        public Builder dbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public Builder dbVersion(int version) {
            dbVersion = version;
            return this;
        }

        public EasySQLiteHelper build() {
            if (context == null) {
                throw new IllegalArgumentException("context can not be null");
            }
            if (TextUtils.isEmpty(dbName)) {
                throw new IllegalArgumentException("dbName can not be null");
            }
            if (dbVersion <= 0) {
                throw new IllegalArgumentException("dbVersion can not less than 0");
            }
            sInstance = new EasySQLiteHelper(this);
            return sInstance;
        }
    }
}
