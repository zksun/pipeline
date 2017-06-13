package com.sun.pipeline.stock;

/**
 * Created by zksun on 13/06/2017.
 */
public enum Trade {
    SHANGHAI(0, "sh"), SHENZHEN(1, "sz");

    private String name;
    private int code;

    Trade(int code, String name) {
        this.name = name;
        this.code = code;
    }


}
