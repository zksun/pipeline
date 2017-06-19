package com.sun.pipeline.stock;

/**
 * Created by hanshou on 19/06/2017.
 */
public final class StockUtil {

    public static int getRealSequence(int source) {
        if (source < 780) {
            return source - 570;
        } else {
            return source - 660;
        }
    }

    private StockUtil() {
    }
}
