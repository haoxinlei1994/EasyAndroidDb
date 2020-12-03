package com.mrh.db_complier.code;

import com.mrh.db_complier.DaoInfo;

import java.util.Map;

import javax.annotation.processing.Filer;

/**
 * Created by haoxinlei on 2020/12/3.
 */
public interface DaoFileGenerator {

    /**
     * 生成Dao类文件
     * @param daoProxyMap
     * @param filer
     */
    void generateDaoFiles(Map<String, DaoInfo> daoProxyMap, Filer filer);
}
