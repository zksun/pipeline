package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.stock.StockKlineStep;
import com.sun.pipeline.stock.explorer.ExplorerContext;
import com.sun.pipeline.stock.explorer.sohu.SohuStockExplorerContext.StockContext;

import java.time.LocalDate;

/**
 * Created by zksun on 19/06/2017.
 */
public final class SohuStockExplorerContext implements ExplorerContext<StockContext> {



    @Override
    public StockContext getContext() {
        return null;
    }

    public static class StockContext {
        private String stockCode;
        private StockKlineStep stockKlineStep;
        private LocalDate start;
        private int limit;

        public StockContext(String stockCode, StockKlineStep stockKlineStep, LocalDate start, int limit) {
            this.stockCode = stockCode;
            this.stockKlineStep = stockKlineStep;
            this.start = start;
            this.limit = limit;
        }

    }
}
