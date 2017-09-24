package com.sun.pipeline.stock.system;

import org.junit.Test;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class SystemConfigTest {
    @Test
    public void getStockDataPathTest() throws Exception {
        String path = SystemConfig.getInstance().getProp("com.sun.stock.date.path", "");
        System.out.println(path);
    }
}
