package com.sun.pipeline.stock.domain;

import java.time.LocalDate;

/**
 * Created by zksun on 19/06/2017.
 */
public class ExcludeRights {

    private Long distribute;
    private Integer exchangeStock;
    private Integer allotmentStock;
    private Long allotmentPrice;
    private LocalDate adjustDay;
    private String stockCode;

    public ExcludeRights(String stockCode) {
        this.stockCode = stockCode;
    }

    public Long getDistribute() {
        return distribute;
    }

    public void setDistribute(Long distribute) {
        this.distribute = distribute;
    }

    public Integer getExchangeStock() {
        return exchangeStock;
    }

    public void setExchangeStock(Integer exchangeStock) {
        this.exchangeStock = exchangeStock;
    }

    public Integer getAllotmentStock() {
        return allotmentStock;
    }

    public void setAllotmentStock(Integer allotmentStock) {
        this.allotmentStock = allotmentStock;
    }

    public Long getAllotmentPrice() {
        return allotmentPrice;
    }

    public void setAllotmentPrice(Long allotmentPrice) {
        this.allotmentPrice = allotmentPrice;
    }

    public LocalDate getAdjustDay() {
        return adjustDay;
    }

    public void setAdjustDay(LocalDate adjustDay) {
        this.adjustDay = adjustDay;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
