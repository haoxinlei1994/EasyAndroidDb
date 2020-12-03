package com.mrh.db_complier.finder;

import com.mrh.db_complier.DaoInfo;

import java.util.Map;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.util.Elements;

/**
 * Created by haoxinlei on 2020/12/3.
 */
public interface DaoInfoFinder {
    void findDaoProxy(RoundEnvironment roundEnvironment, Map<String, DaoInfo> daoProxyMap, Elements elements);
}
