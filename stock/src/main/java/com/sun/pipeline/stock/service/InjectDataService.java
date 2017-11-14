package com.sun.pipeline.stock.service;

import java.time.LocalDate;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public interface InjectDataService {
    boolean injectStockData(String stockCode, LocalDate start, LocalDate end);

    boolean injectAllStockData(LocalDate start, LocalDate end);

    boolean injectAllStockCode();

    boolean injectAllStockExcludeRights();
}
