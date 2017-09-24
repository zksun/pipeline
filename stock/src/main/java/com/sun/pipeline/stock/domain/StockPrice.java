package com.sun.pipeline.stock.domain;

/**
 * Created by zksun on 13/06/2017.
 */
public class StockPrice {

    private Stock stock;
    private TimeDomain time;
    private Long price;

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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
