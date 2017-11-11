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
    private String macket;

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

    public String getMacket() {
        return macket;
    }

    public void setMacket(String macket) {
        this.macket = macket;
    }
}