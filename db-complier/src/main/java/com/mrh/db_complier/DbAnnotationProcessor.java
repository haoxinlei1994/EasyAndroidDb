package com.mrh.db_complier;

import com.google.auto.service.AutoService;
import com.mrh.db_annotation.Column;
import com.mrh.db_annotation.Table;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class DbAnnotationProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private Messager mMessage;
    private Filer mFiler;
    private Map<String, DaoProxy> mDaoProxyMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnv.getElementUtils();
        mMessage = processingEnv.getMessager();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(Table.class.getCanonicalName());
        annotationTypes.add(Column.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        new DaoProxyFinder().findDaoProxy(roundEnvironment, mDaoProxyMap, mElementUtils);
        new DaoFileGenerator().generateDaoFiles(mDaoProxyMap, mFiler);
        return false;
    }
}
