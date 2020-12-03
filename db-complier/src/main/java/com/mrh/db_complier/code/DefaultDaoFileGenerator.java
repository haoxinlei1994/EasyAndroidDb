package com.mrh.db_complier.code;

import com.mrh.db_annotation.Column;
import com.mrh.db_complier.DaoInfo;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * 根据所搜集的 DaoInfo 信息，生成 Dao文件
 * Created by haoxinlei on 2020/7/13.
 */
public class DefaultDaoFileGenerator implements DaoFileGenerator{

    private static final ClassName CONTENT_VALUES = ClassName.get("android.content", "ContentValues");
    private static final ClassName CURSOR = ClassName.get("android.database", "Cursor");
    private static Map<String, String> sTypeMethodMap = new HashMap<>();

    static {
        sTypeMethodMap.put("java.lang.String", "getString");
        sTypeMethodMap.put("java.lang.Integer", "getInt");
        sTypeMethodMap.put("int", "getInt");
        sTypeMethodMap.put("java.lang.Short", "getShort");
        sTypeMethodMap.put("short", "getShort");
        sTypeMethodMap.put("java.lang.Long", "getLong");
        sTypeMethodMap.put("long", "getLong");
        sTypeMethodMap.put("java.lang.Float", "getFloat");
        sTypeMethodMap.put("float", "getFloat");
        sTypeMethodMap.put("java.lang.Double", "getDouble");
        sTypeMethodMap.put("double", "getDouble");
    }

    @Override
    public void generateDaoFiles(Map<String, DaoInfo> daoProxyMap, Filer filer) {
        if (daoProxyMap == null || daoProxyMap.size() == 0) {
            return;
        }
        Iterator<String> iterator = daoProxyMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            DaoInfo daoInfo = daoProxyMap.get(key);
            if (daoInfo != null) {
                generateDaoFile(daoInfo, filer);
            }
        }
    }

    /**
     * 生成相应的 Dao.java 文件
     *
     * @param daoInfo
     * @param filer
     */
    private void generateDaoFile(DaoInfo daoInfo, Filer filer) {
        TypeSpec typeSpec = buildTypeSpace(daoInfo);
        JavaFile javaFile = JavaFile.builder(daoInfo.packageName, typeSpec)
                .addFileComment("generate from EasyAndroidDb, don't edit")
                .build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建Dao类型
     *
     * @param daoInfo
     * @return
     */
    private TypeSpec buildTypeSpace(DaoInfo daoInfo) {
        ClassName superType = ClassName.get("com.mrh.database.dao", daoInfo.isAsync ? "AsyncDao" : "SyncDao");
        return TypeSpec.classBuilder(daoInfo.getGeneratedFileSimpleClassName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(superType, ClassName.get(daoInfo.packageName, daoInfo.classSimpleName)))
                .addMethod(buildConstructor(daoInfo))
                .addMethod(buildConvertObject(daoInfo))
                .addMethod(buildParseResult(daoInfo))
                .build();
    }

    /**
     * 创建构造方法
     *
     * @param daoInfo
     * @return
     */
    private MethodSpec buildConstructor(DaoInfo daoInfo) {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("super($S)", daoInfo.tableName)
                .build();
    }

    /**
     * 生成 Object 转化 ContentValues 方法
     *
     * @param daoInfo
     * @return
     */
    private MethodSpec buildConvertObject(DaoInfo daoInfo) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("convertObject")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(CONTENT_VALUES)
                .addParameter(ClassName.get(daoInfo.packageName, daoInfo.classSimpleName), "obj")
                .addStatement("ContentValues contentValues = new ContentValues()");
        for (int i = 0; i < daoInfo.variableElements.size(); i++) {
            VariableElement field = daoInfo.variableElements.get(i);
            methodBuilder.addStatement("contentValues.put($S, obj.$L)", getColumnName(field), field.getSimpleName().toString());
        }
        methodBuilder.addStatement("return contentValues");
        return methodBuilder.build();
    }

    /**
     * 生成 cursor 解析 对象 方法
     *
     * @param daoInfo
     * @return
     */
    private MethodSpec buildParseResult(DaoInfo daoInfo) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("parseResult")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(daoInfo.packageName, daoInfo.classSimpleName))
                .addParameter(CURSOR, "cursor")
                .addAnnotation(Override.class)
                .addStatement("$L object = new $L()", daoInfo.classSimpleName, daoInfo.classSimpleName);
        for (int i = 0; i < daoInfo.variableElements.size(); i++) {
            VariableElement field = daoInfo.variableElements.get(i);
            methodBuilder.addStatement("object.$L = cursor.$L(cursor.getColumnIndex($S))", field.getSimpleName(), sTypeMethodMap.get(field.asType().toString()), getColumnName(field));
        }
        methodBuilder.addStatement("return object");
        return methodBuilder.build();
    }

    /**
     * 获取列名
     * @param field
     * @return
     */
    private String getColumnName(VariableElement field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        String tableName = columnAnnotation.value();
        if (tableName == null || tableName.length() == 0) {
            tableName = field.getSimpleName().toString();
        }
        return tableName;
    }

}
