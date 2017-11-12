package com.sun.pipeline.mybatis.dao;

import com.sun.pipeline.mybatis.domain.StockBaseDO;

public interface StockBaseDAO extends GenericCrudDAO<StockBaseDO> {
    int clear();
}