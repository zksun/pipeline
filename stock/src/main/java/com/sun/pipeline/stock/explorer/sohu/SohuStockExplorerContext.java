package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.StockKlineStep;
import com.sun.pipeline.stock.explorer.AbstractStockExplorerContext;
import com.sun.pipeline.stock.explorer.ExplorerContext;

import java.time.LocalDate;

/**
 * Created by zksun on 19/06/2017.
 */
public final class SohuStockExplorerContext extends AbstractStockExplorerContext {



    @Override
    protected String fireHttpGetInformationName() {
        return null;
    }

    @Override
    public ExplorerContext fireInformationHandler(String name) {
        return null;
    }



    @Override
    public Object getValue() {
        return null;
    }

    public static class StockQuery {
        private String stockCode;
        private StockKlineStep stockKlineStep;
        private LocalDate start;
        private int limit;

        public StockQuery(String stockCode, StockKlineStep stockKlineStep, LocalDate start, int limit) {
            this.stockCode = stockCode;
            this.stockKlineStep = stockKlineStep;
            this.start = start;
            this.limit = limit;
        }

    }
}
