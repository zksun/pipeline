package com.sun.pipeline.stock.domain;

import java.time.LocalDate;

/**
 * Created by zksun on 2017/6/29.
 */
public class KlineItem {

    private Long openPrice;

    private Long closePrice;

    private Long highPrice;

    private Long lowPrice;

    private Long tradedVolume;

    private Long tradedAmount;

    private Double handRate;

    private Long upDown;

    private Double upDownRatio;

    private LocalDate date;

    private String stockCode;


    public Long getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Long openPrice) {
        this.openPrice = openPrice;
    }

    public Long getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Long closePrice) {
        this.closePrice = closePrice;
    }

    public Long getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Long highPrice) {
        this.highPrice = highPrice;
    }

    public Long getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Long lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Long getTradedVolume() {
        return tradedVolume;
    }

    public void setTradedVolume(Long tradedVolume) {
        this.tradedVolume = tradedVolume;
    }

    public Long getTradedAmount() {
        return tradedAmount;
    }

    public void setTradedAmount(Long tradedAmount) {
        this.tradedAmount = tradedAmount;
    }

    public Double getHandRate() {
        return handRate;
    }

    public void setHandRate(Double handRate) {
        this.handRate = handRate;
    }

    public Long getUpDown() {
        return upDown;
    }

    public void setUpDown(Long upDown) {
        this.upDown = upDown;
    }

    public Double getUpDownRatio() {
        return upDownRatio;
    }

    public void setUpDownRatio(Double upDownRatio) {
        this.upDownRatio = upDownRatio;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }
}
