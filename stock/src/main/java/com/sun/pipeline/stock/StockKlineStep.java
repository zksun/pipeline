package com.sun.pipeline.stock;

/**
 * Created by zksun on 13/06/2017.
 */
public enum StockKlineStep implements Time {
    SECOND(0, "s"), MINUTE(1, "m"), MINUTE_5(2, "5m"), QUARTER_HOUR(3, "15m"), HALF_HOUR(4, "30m"),
    HOUR(5, "h"), DAY(6, "day"), DAY_5(7, "5day"), DAY_10(8, "10day"), DAY_30(9, "30day"), MONTH(10, "month");


    public String getTime() {
        return null;
    }

    private int sequence;
    private String time;

    StockKlineStep(int sequence, String time) {
        this.sequence = sequence;
        this.time = time;
    }
}
