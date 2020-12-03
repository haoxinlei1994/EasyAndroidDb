package com.mrh.db_complier;

import com.google.auto.service.AutoService;
import com.mrh.db_annotation.Column;
import com.mrh.db_annotation.Table;
import com.mrh.db_complier.code.DaoFileGenerator;
import com.mrh.db_complier.code.DefaultDaoFileGenerator;
import com.mrh.db_complier.finder.DaoInfoFinder;
import com.mrh.db_complier.finder.DefaultDaoInfoFinder;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 注解处理器
 */
@AutoService(Processor.class)
public class DbAnnotationProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private Filer mFiler;
    private Map<String, DaoInfo> mDaoInfoMap = new HashMap<>();
    private DaoInfoFinder mDaoInfoFinder = new DefaultDaoInfoFinder();
    private DaoFileGenerator mDaoFileGenerator = new DefaultDaoFileGenerator();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mElementUtils = processingEnv.getElementUtils();
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

    /**
     * 搜集注解信息，生成Dao文件
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mDaoInfoFinder.findDaoProxy(roundEnvironment, mDaoInfoMap, mElementUtils);
        mDaoFileGenerator.generateDaoFiles(mDaoInfoMap, mFiler);
        return false;
    }
}
