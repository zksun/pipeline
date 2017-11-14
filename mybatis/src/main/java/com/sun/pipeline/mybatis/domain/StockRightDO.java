package com.sun.pipeline.mybatis.domain;

import java.util.Date;

public class StockRightDO {
    /**
     *  
     */
    private Long id;

    /**
     *  
     */
    private String stockCode;

    /**
     *  
     */
    private Long distribute;

    /**
     *  
     */
    private Integer exchangeStock;

    /**
     *  
     */
    private Integer allotmentStock;

    /**
     *  
     */
    private Long allotmentPrice;

    /**
     *  
     */
    private Date adjustDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
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

    public Date getAdjustDay() {
        return adjustDay;
    }

    public void setAdjustDay(Date adjustDay) {
        this.adjustDay = adjustDay;
    }
}