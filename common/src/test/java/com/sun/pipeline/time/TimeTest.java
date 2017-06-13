package com.sun.pipeline.time;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by zksun on 13/06/2017.
 */
public class TimeTest {

    @Test
    public void timeYearDayMinuteSecondTest() {
        LocalDateTime localDateTime = LocalDateTime.of(2015, 4, 23, 9, 1);
        System.out.println(localDateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime parse = LocalDateTime.parse("2015/12/26 09:00:00", formatter);
        System.out.println(parse);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println(formatter1.format(localDateTime));
    }

}
