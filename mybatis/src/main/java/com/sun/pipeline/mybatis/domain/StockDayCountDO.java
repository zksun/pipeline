package com.sun.pipeline.mybatis.domain;

import java.util.Date;

public class StockDayCountDO {
    /**
     *  
     */
    private Long id;

    /**
     *  
     */
    private String code;

    /**
     *  
     */
    private Date day;

    /**
     *  
     */
    private Integer avg;

    /**
     *  
     */
    private Integer totalHand;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Integer getAvg() {
        return avg;
    }

    public void setAvg(Integer avg) {
        this.avg = avg;
    }

    public Integer getTotalHand() {
        return totalHand;
    }

    public void setTotalHand(Integer totalHand) {
        this.totalHand = totalHand;
    }
}