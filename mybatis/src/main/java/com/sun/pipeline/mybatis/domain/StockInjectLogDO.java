package com.sun.pipeline.mybatis.domain;

import java.util.Date;

public class StockInjectLogDO {
    /**
     *  
     */
    private Integer id;

    /**
     *  
     */
    private String fullName;

    /**
     *  
     */
    private String date;

    /**
     *  
     */
    private Date injectTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getInjectTime() {
        return injectTime;
    }

    public void setInjectTime(Date injectTime) {
        this.injectTime = injectTime;
    }
}