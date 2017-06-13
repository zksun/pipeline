package com.sun.pipeline.time;

import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by zksun on 13/06/2017.
 */
public class TimeTest {

    @Test
    public void timeMinuteSecondTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2015, 4, 23, 9, 1, 0);
        System.out.println(localDateTime);
    }
}
