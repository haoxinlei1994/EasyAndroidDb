package com.mrh.db_complier;

import com.mrh.db_annotation.Column;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

/**
 * 根据所搜集的 DaoInfo 信息，生成 Dao文件
 * Created by haoxinlei on 2020/7/13.
 */
public class DaoFileGenerator {

    private static final String CLAUSE_END = ";\n";
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
        StringBuilder fileBuilder = new StringBuilder();
        fileBuilder.append("package " + daoInfo.packageName + CLAUSE_END)
                .append("\nimport android.content.ContentValues;\n")
                .append("import android.database.Cursor;\n")
                .append("import com.mrh.database.dao." + (daoInfo.isAsync ? "AsyncDao" : "SyncDao") + CLAUSE_END)
                .append("import " + daoInfo.className + CLAUSE_END)
                .append("/**\n* auto generate,do not edit!!\n*/")
                .append("\npublic class " + daoInfo.getGeneratedJavaFileName() + " extends " + (daoInfo.isAsync ? "AsyncDao<" : "SyncDao<") + daoInfo.classSimpleName + "> {\n")
                .append(generateConstructor(daoInfo))
                .append(generateConvertObject(daoInfo))
                .append(generateParseResult(daoInfo))
                .append("}");
        Writer writer = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(daoInfo.getGeneratedJavaFileName(), daoInfo.typeElement);
            writer = sourceFile.openWriter();
            writer.write(fileBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成构造函数
     *
     * @param daoInfo
     * @return
     */
    private String generateConstructor(DaoInfo daoInfo) {
        return new StringBuilder()
                .append("   public " + daoInfo.classSimpleName + "Dao() {\n")
                .append("      super(\"" + daoInfo.tableName + "\");\n")
                .append("   }\n\n")
                .toString();
    }

    /**
     * 生成 Object 转化 ContentValues 方法
     *
     * @param daoInfo
     * @return
     */
    private String generateConvertObject(DaoInfo daoInfo) {
        StringBuilder method = new StringBuilder()
                .append("   @Override\n")
                .append("   public ContentValues convertObject(" + daoInfo.classSimpleName + " obj) {\n")
                .append("     ContentValues contentValues = new ContentValues();\n");
        for (int i = 0; i < daoInfo.variableElements.size(); i++) {
            VariableElement field = daoInfo.variableElements.get(i);
            method.append("     contentValues.put(\"" + getColumnName(field) + "\", obj." + field.getSimpleName().toString() + ");\n");
        }
        return method.append("      return contentValues;\n")
                .append("   }\n\n")
                .toString();
    }

    /**
     * 生成 cursor 解析 对象 方法
     *
     * @param daoInfo
     * @return
     */
    private String generateParseResult(DaoInfo daoInfo) {
        StringBuilder method = new StringBuilder()
                .append("   @Override\n")
                .append("   public " + daoInfo.classSimpleName + " parseResult(Cursor cursor) {\n")
                .append("       " + daoInfo.classSimpleName + " person = new " + daoInfo.classSimpleName + "();\n");
        for (int i = 0; i < daoInfo.variableElements.size(); i++) {
            VariableElement field = daoInfo.variableElements.get(i);
            method.append("       person." + field.getSimpleName() + " = cursor." + sTypeMethodMap.get(field.asType().toString()) + "(cursor.getColumnIndex(\"" + getColumnName(field) + "\"));\n");
        }
        return method
                .append("       return person;\n")
                .append("   }\n")
                .toString();
    }

    private String getColumnName(VariableElement field) {
        Column columnAnnotation = field.getAnnotation(Column.class);
        String tableName = columnAnnotation.value();
        if (tableName == null || tableName.length() == 0) {
            tableName = field.getSimpleName().toString();
        }
        return tableName;
    }

}
