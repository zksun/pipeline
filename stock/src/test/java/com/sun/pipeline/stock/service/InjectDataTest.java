package com.sun.pipeline.stock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
}