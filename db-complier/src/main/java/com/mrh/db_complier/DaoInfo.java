package com.mrh.db_complier;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * 保存被 {@link com.mrh.db_annotation.Table}注解修饰对象的所有信息
 * Created by haoxinlei on 2020/7/13.
 */
public class DaoInfo {
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
    /**
     * 被 {@link com.mrh.db_annotation.Table} 修饰的类
     */
    public TypeElement typeElement;
    /**
     * 被 {@link com.mrh.db_annotation.Column} 修饰的属性
     */
    public List<VariableElement> variableElements = new ArrayList<>();

    /**
     * 获取生成java文件的name
     * @return
     */
    public String getGeneratedJavaFileName() {
        return classSimpleName + "Dao";
    }
}
