package com.mrh.db_complier.finder;

import com.mrh.db_annotation.Column;
import com.mrh.db_annotation.Table;
import com.mrh.db_complier.DaoInfo;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * 搜集所有被 {@link com.mrh.db_annotation.Table} 修饰的类，
 * 以及类中被 {@link com.mrh.db_annotation.Column} 修饰的属性
 * <p>
 * Created by haoxinlei on 2020/7/13.
 */
public class DefaultDaoInfoFinder implements DaoInfoFinder {

    @Override
    public void findDaoProxy(RoundEnvironment roundEnvironment, Map<String, DaoInfo> daoProxyMap, Elements elements) {
        daoProxyMap.clear();
        findDaoTableAnnotation(roundEnvironment, daoProxyMap, elements);
        findColumnAnnotation(roundEnvironment, daoProxyMap);
    }

    /**
     * 搜集所有 {@link com.mrh.db_annotation.Table} 修饰的类，并将类的信息保存
     * @param roundEnvironment
     * @param daoProxyMap
     * @param elements
     */
    private void findDaoTableAnnotation(RoundEnvironment roundEnvironment, Map<String, DaoInfo> daoProxyMap, Elements elements) {
        Set<? extends Element> tableAnnotations = roundEnvironment.getElementsAnnotatedWith(Table.class);
        if (tableAnnotations != null) {
            for (Element element : tableAnnotations) {
                TypeElement typeElement = (TypeElement) element;
                PackageElement packageElement = elements.getPackageOf(element);
                String className = typeElement.getQualifiedName().toString();
                Table table = typeElement.getAnnotation(Table.class);
                DaoInfo daoInfo = new DaoInfo();
                daoInfo.packageName = packageElement.getQualifiedName().toString();
                daoInfo.className = className;
                daoInfo.classSimpleName = typeElement.getSimpleName().toString();
                daoInfo.typeElement = typeElement;
                daoInfo.isAsync = table.isAsync();
                daoInfo.tableName = table.tableName();
                daoProxyMap.put(className, daoInfo);
            }
        }
    }

    /**
     * 搜集所有 {@link com.mrh.db_annotation.Column} 修饰的属性并保存
     * @param roundEnvironment
     * @param daoProxyMap
     */
    private void findColumnAnnotation(RoundEnvironment roundEnvironment, Map<String, DaoInfo> daoProxyMap) {
        Set<? extends Element> columnAnnotations = roundEnvironment.getElementsAnnotatedWith(Column.class);
        if (columnAnnotations != null) {
            for (Element element : columnAnnotations) {
                VariableElement variableElement = (VariableElement) element;
                TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
                DaoInfo daoInfo = daoProxyMap.get(typeElement.getQualifiedName().toString());
                if (daoInfo != null) {
                    daoInfo.variableElements.add(variableElement);
                }
            }
        }
    }
}
