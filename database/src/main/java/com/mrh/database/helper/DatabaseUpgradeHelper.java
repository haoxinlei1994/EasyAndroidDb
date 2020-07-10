package com.mrh.database.helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 数据库升级帮助类
 * 读取 assets/upgrade 目录下的满足 "^(upgrade_)\d+(.sql)$" 正则的数据库升级sql文件，
 * 根据 oldVersion 和 newVersion 进行筛选
 * Created by haoxinlei on 2020/7/7.
 */
public class DatabaseUpgradeHelper {
    private static final String UPGRADE_DIE = "upgrade";

    private AssetSqlExecutor mAssetSqlExecutor = new AssetSqlExecutor();

    public void upgrade(Context context, SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }
        try {
            AssetManager assetManager = context.getAssets();
            List<String> upgradeFiles = getUpgradeFiles(assetManager, oldVersion, newVersion);
            if (upgradeFiles != null && upgradeFiles.size() > 0) {
                sqLiteDatabase.beginTransaction();
                for (int i = 0; i < upgradeFiles.size(); i++) {
                    mAssetSqlExecutor.executeSql(getFilePath(upgradeFiles.get(i)), assetManager, sqLiteDatabase);
                }
                sqLiteDatabase.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    /**
     * 获取所有满足条件的升级sql文件
     * 1、assets/upgrade 目录下
     * 2、满足"^(upgrade_)\d+(.sql)$"正则
     * 3、 oleVersion < version <= newVersion
     * @param assetManager
     * @param oldVersion
     * @param newVersion
     * @return
     */
    private List<String> getUpgradeFiles(AssetManager assetManager, int oldVersion, int newVersion) {
        List<String> files = null;
        try {
            files = Arrays.asList(assetManager.list(UPGRADE_DIE));
            Collections.sort(files, (s, target) -> s.compareTo(target));
            Iterator<String> iterator = files.iterator();
            while (iterator.hasNext()) {
                String file = iterator.next();
                if (!Pattern.matches("^(upgrade_)\\d+(.sql)$", file)) {
                    iterator.remove();
                }
                // eg: upgrade_3.sql 解析后 version = 3
                int version = Integer.valueOf(file.split("\\.")[0].split("_")[1]);
                if (version <= oldVersion || version > newVersion) {
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    private String getFilePath(String fileName) {
        return UPGRADE_DIE + File.separator + fileName;
    }
}
