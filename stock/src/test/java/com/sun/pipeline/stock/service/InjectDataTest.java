package com.sun.pipeline.stock.service;

import com.sun.pipeline.stock.StockUtil;
import com.sun.pipeline.stock.domain.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by zhikunsun on 2017/11/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class InjectDataTest {
    @Resource
    private InjectDataService injectDataService;

    @Test
    public void injectStockCodeTest() {
        boolean b = injectDataService.injectAllStockCode();
        System.out.println(b);
    }

    @Test
    public void setInjectDayContainerTest() {
        LocalDate start = LocalDate.parse("20170921", DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate end = LocalDate.parse("20170921", DateTimeFormatter.ofPattern("yyyyMMdd"));
        boolean b = injectDataService.injectStockData("sh600000", start, end);
        System.out.println(b);
    }
}