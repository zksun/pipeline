package com.sun.pipeline.stock.service.impl;

import com.sun.pipeline.stock.service.InjectDataService;

import java.time.LocalDate;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class InjectDataServiceImpl implements InjectDataService {

    private String path;

    @Override
    public boolean injectStockData(String stockCode, LocalDate start, LocalDate end) {
        return false;
    }
}
