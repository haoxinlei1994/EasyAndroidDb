package com.mrh.db_complier;

import com.mrh.db_annotation.Column;
import com.mrh.db_annotation.Table;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by haoxinlei on 2020/7/13.
 */
public class DaoProxyFinder {

    public void findDaoProxy(RoundEnvironment roundEnvironment, Map<String, DaoProxy> daoProxyMap, Elements elements) {
        daoProxyMap.clear();
        findDaoTableAnnotation(roundEnvironment, daoProxyMap, elements);
        findColumnAnnotation(roundEnvironment, daoProxyMap);
    }

    private void findDaoTableAnnotation(RoundEnvironment roundEnvironment, Map<String, DaoProxy> daoProxyMap, Elements elements) {
        Set<? extends Element> tableAnnotations = roundEnvironment.getElementsAnnotatedWith(Table.class);
        if (tableAnnotations != null) {
            for (Element element : tableAnnotations) {
                TypeElement typeElement = (TypeElement) element;
                PackageElement packageElement = elements.getPackageOf(element);
                String className = typeElement.getQualifiedName().toString();
                Table table = typeElement.getAnnotation(Table.class);
                DaoProxy daoProxy = new DaoProxy();
                daoProxy.packageName = packageElement.getQualifiedName().toString();
                daoProxy.className = className;
                daoProxy.classSimpleName = typeElement.getSimpleName().toString();
                daoProxy.typeElement = typeElement;
                daoProxy.isAsync = table.isAsync();
                daoProxy.tableName = table.tableName();
                daoProxyMap.put(className, daoProxy);
            }
        }
    }

    private void findColumnAnnotation(RoundEnvironment roundEnvironment, Map<String, DaoProxy> daoProxyMap) {
        Set<? extends Element> columnAnnotations = roundEnvironment.getElementsAnnotatedWith(Column.class);
        if (columnAnnotations != null) {
            for (Element element : columnAnnotations) {
                VariableElement variableElement = (VariableElement) element;
                TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
                DaoProxy daoProxy = daoProxyMap.get(typeElement.getQualifiedName().toString());
                if (daoProxy != null) {
                    daoProxy.variableElements.add(variableElement);
                }
            }
        }
    }
}
