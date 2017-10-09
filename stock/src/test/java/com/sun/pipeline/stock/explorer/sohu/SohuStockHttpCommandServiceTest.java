package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.util.internal.http.HttpGet;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Created by hanshou on 2017/9/21.
 */
public class SohuStockHttpCommandServiceTest {
    @Test
    public void calculateAdjustStockPriceTest() {
        long sz_600030 = SohuStockHttpCommandService.getInstance()
                .calculateAdjustStockPrice(1619, "sz_600030", LocalDate.of(2017, 4, 27), HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3));
        System.out.println(sz_600030);

    }
}
