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

    public void generateDaoFiles(Map<String, DaoProxy> daoProxyMap, Filer filer) {
        if (daoProxyMap == null || daoProxyMap.size() == 0) {
            return;
        }
        Iterator<String> iterator = daoProxyMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            DaoProxy daoProxy = daoProxyMap.get(key);
            if (daoProxy != null) {
                generateDaoFile(daoProxy, filer);
            }
        }
    }

    private void generateDaoFile(DaoProxy daoProxy, Filer filer) {
        StringBuilder fileBuilder = new StringBuilder();
        fileBuilder.append("package " + daoProxy.packageName + CLAUSE_END)
                .append("\nimport android.content.ContentValues;\n")
                .append("import android.database.Cursor;\n")
                .append("import com.mrh.database.dao." + (daoProxy.isAsync ? "AsyncDao" : "SyncDao") + CLAUSE_END)
                .append("import " + daoProxy.className + CLAUSE_END)
                .append("/**\n* auto generate,do not edit!!\n*/")
                .append("\npublic class " + daoProxy.getGeneratedJavaFileName() + " extends " + (daoProxy.isAsync ? "AsyncDao<" : "SyncDao<") + daoProxy.classSimpleName + "> {\n")
                .append(generateConstructor(daoProxy))
                .append(generateConvertObject(daoProxy))
                .append(generateParseResult(daoProxy))
                .append("}");
        Writer writer = null;
        try {
            JavaFileObject sourceFile = filer.createSourceFile(daoProxy.getGeneratedJavaFileName(), daoProxy.typeElement);
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
     * @param daoProxy
     * @return
     */
    private String generateConstructor(DaoProxy daoProxy) {
        return new StringBuilder()
                .append("   public " + daoProxy.classSimpleName + "Dao() {\n")
                .append("      super(\"" + daoProxy.tableName + "\");\n")
                .append("   }\n\n")
                .toString();
    }

    /**
     * 生成 Object 转化 ContentValues 方法
     *
     * @param daoProxy
     * @return
     */
    private String generateConvertObject(DaoProxy daoProxy) {
        StringBuilder method = new StringBuilder()
                .append("   @Override\n")
                .append("   public ContentValues convertObject(" + daoProxy.classSimpleName + " obj) {\n")
                .append("     ContentValues contentValues = new ContentValues();\n");
        for (int i = 0; i < daoProxy.variableElements.size(); i++) {
            VariableElement field = daoProxy.variableElements.get(i);
            Column columnAnnotation = field.getAnnotation(Column.class);
            method.append("     contentValues.put(\"" + columnAnnotation.value() + "\", obj." + field.getSimpleName().toString() + ");\n");
        }
        return method.append("      return contentValues;\n")
                .append("   }\n\n")
                .toString();
    }
    /**
     * 生成 cursor 解析 对象 方法
     *
     * @param daoProxy
     * @return
     */
    private String generateParseResult(DaoProxy daoProxy) {
        StringBuilder method = new StringBuilder()
                .append("   @Override\n")
                .append("   public " + daoProxy.classSimpleName + " parseResult(Cursor cursor) {\n")
                .append("       " + daoProxy.classSimpleName + " person = new " + daoProxy.classSimpleName + "();\n");
        for (int i = 0; i < daoProxy.variableElements.size(); i++) {
            VariableElement field = daoProxy.variableElements.get(i);
            Column columnAnnotation = field.getAnnotation(Column.class);
            method.append("       person." + field.getSimpleName() + " = cursor." + sTypeMethodMap.get(field.asType().toString()) + "(cursor.getColumnIndex(\"" + columnAnnotation.value() + "\"));\n");
        }
        return method
                .append("       return person;\n")
                .append("   }\n")
                .toString();
    }
}
