package com.sun.pipeline.stock.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by zksun on 13/06/2017.
 */
public class TimeDomain {

    private LocalDate dateTime;

    public TimeDomain(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

}
