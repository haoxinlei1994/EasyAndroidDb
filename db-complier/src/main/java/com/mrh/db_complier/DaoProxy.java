package com.mrh.db_complier;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * Created by haoxinlei on 2020/7/13.
 */
public class DaoProxy {
    /**
     * 包名
     */
    public String packageName;
    /**
     * 全类名
     */
    public String className;
    /**
     * 类名
     */
    public String classSimpleName;
    /**
     * 表名
     */
    public String tableName;
    /**
     * 是否为异步dao
     */
    public boolean isAsync;

    public TypeElement typeElement;

    public List<VariableElement> variableElements = new ArrayList<>();

    /**
     * 获取生成java文件的name
     * @return
     */
    public String getGeneratedJavaFileName() {
        return classSimpleName + "Dao";
    }
}
