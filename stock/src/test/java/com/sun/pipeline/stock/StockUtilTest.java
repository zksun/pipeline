package com.sun.pipeline.stock;

import org.junit.Test;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class StockUtilTest {
    @Test
    public void getRealStockCode(){
        String source =  "sz0000456";
        String realStockCode = StockUtil.getRealStockCode(source);
        System.out.println(realStockCode);
    }
}
