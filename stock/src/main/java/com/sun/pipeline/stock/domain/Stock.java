package com.sun.pipeline.stock.domain;

import com.sun.pipeline.stock.Trade;

/**
 * Created by zksun on 13/06/2017.
 */
public class Stock {

    private Trade trade;
    private String stockCode;

    public Stock(Trade trade, String stockCode) {
        this.trade = trade;
        this.stockCode = stockCode;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
