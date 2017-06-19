package com.sun.pipeline.stock.domain;

import java.time.LocalDateTime;

/**
 * Created by zksun on 13/06/2017.
 */
public class TimeDomain {

    private LocalDateTime dateTime;

    public TimeDomain(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

}
