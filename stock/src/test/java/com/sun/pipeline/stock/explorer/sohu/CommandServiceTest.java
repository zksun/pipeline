package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.util.internal.http.HttpGet;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by zksun on 2017/9/20.
 */
public class CommandServiceTest {
    @Test
    public void calculateAllotmentPrice() {
        SohuStockHttpCommandService instance = SohuStockHttpCommandService.getInstance();
        HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        Long sh601688 = instance.calculateAllotmentPrice("sh_601688", LocalDate.now(), httpGetInstance);
        System.out.println(sh601688);
    }
}
