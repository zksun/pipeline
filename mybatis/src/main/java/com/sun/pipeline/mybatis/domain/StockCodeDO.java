package com.sun.pipeline.mybatis.domain;

public class StockCodeDO {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    private String fullName;

    /**
     *
     */
    private Integer code;

    /**
     *
     */
    private String market;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    @Override
    public String toString() {
        return "StockCodeDO{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", code=" + code +
                ", market='" + market + '\'' +
                '}';
    }
}