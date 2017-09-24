package com.sun.pipeline.stock.domain;

/**
 * Created by zhikunsun on 17/9/24.
 */
public enum Trade {
    BUY(0), SELL(1), UNKNOWN(2);

    private int code;

    Trade(int code) {
        this.code = code;
    }

    public static Trade getTradeByCode(int code) {
        switch (code) {
            case 0:
                return BUY;
            case 1:
                return SELL;
            default:
                return UNKNOWN;
        }
    }

}
