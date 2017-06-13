package com.sun.pipeline.stock.domain;

import java.util.List;

/**
 * Created by zksun on 13/06/2017.
 */
public class StockPrice {

    private Stock stock;
    private TimeDomain time;
    private List<Integer> prices;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public TimeDomain getTime() {
        return time;
    }

    public void setTime(TimeDomain time) {
        this.time = time;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }
}
