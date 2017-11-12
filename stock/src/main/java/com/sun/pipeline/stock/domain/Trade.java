package com.sun.pipeline.stock.domain;

/**
 * Created by zhikunsun on 17/9/24.
 */
public enum Trade {
    BUY((short) 0), SELL((short) 1), UNKNOWN((short) 2);

    private short code;

    Trade(short code) {
        this.code = code;
    }

    public short getCode() {
        return code;
    }

    public static Trade getTradeByCode(short code) {
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
