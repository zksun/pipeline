package com.sun.pipeline.stock.domain;

/**
 * Created by zksun on 13/06/2017.
 */
public class StockPrice {

    private Stock stock;
    private TimeDomain time;
    private Long price;
    private Integer hand;

    private Trade trade;
    private Long authorityPrice;


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

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(short code) {
        this.trade = Trade.getTradeByCode(code);
    }

    public Long getAuthorityPrice() {
        return authorityPrice;
    }

    public void setAuthorityPrice(Long authorityPrice) {
        this.authorityPrice = authorityPrice;
    }

    @Override
    public String toString() {
        return "StockPrice{" +
                "stock=" + stock +
                ", time=" + time +
                ", price=" + price +
                ", hand=" + hand +
                ", trade=" + trade +
                ", authorityPrice=" + authorityPrice +
                '}';
    }
}
