package com.sun.pipeline.stock.service;

import javax.annotation.Resource;

/**
 * Created by zhikunsun on 2017/11/12.
 */
public class InjectDataTest {
    @Resource
    private InjectDataService injectDataService;

    public void injectStockCodeTest() {
        boolean b = injectDataService.injectAllStockCode();
        System.out.println(b);
    }
}