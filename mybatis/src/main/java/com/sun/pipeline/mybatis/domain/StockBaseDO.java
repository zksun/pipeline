package com.sun.pipeline.mybatis.domain;

import java.util.Date;

public class StockBaseDO {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private Integer code;

    /**
     *
     */
    private Date time;

    /**
     *
     */
    private Long price;

    /**
     *
     */
    private Long exright;

    /**
     *
     */
    private Integer hand;

    /**
     *
     */
    private Byte wish;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getExright() {
        return exright;
    }

    public void setExright(Long exright) {
        this.exright = exright;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Byte getWish() {
        return wish;
    }

    public void setWish(Byte wish) {
        this.wish = wish;
    }

    @Override
    public String toString() {
        return "StockBaseDO{" +
                "id=" + id +
                ", code=" + code +
                ", time=" + time +
                ", price=" + price +
                ", exright=" + exright +
                ", hand=" + hand +
                ", wish=" + wish +
                '}';
    }
}