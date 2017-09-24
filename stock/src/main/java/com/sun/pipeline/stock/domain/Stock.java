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

    public Stock(String stockCode) {
        this.stockCode = stockCode;
        if (stockCode.startsWith("sh")) {
            this.trade = Trade.SHANGHAI;
        } else if (stockCode.startsWith("sz")) {
            this.trade = Trade.SHENZHEN;
        }
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
