package com.sun.pipeline.stock;

/**
 * Created by zksun on 13/06/2017.
 */
public enum Trade {
    SHANGHAI(0, "sh"), SHENZHEN(1, "sz");

    private String name;
    private int code;

    Trade(String name, int code) {
        this.name = name;
        this.code = code;
    }



}
