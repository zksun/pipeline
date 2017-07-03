package com.sun.pipeline.stock;

/**
 * Created by zksun on 19/06/2017.
 */
public final class StockUtil {

    public static int getRealSequence(int source) {
        if (source < 780) {
            return source - 570;
        } else {
            return source - 660;
        }
    }

    public static String getRealStockCode(String code) {
        if (code.split("_").length > 1) {
            return code.substring(3, code.length());
        }
        return code;
    }

    private StockUtil() {
    }
}
