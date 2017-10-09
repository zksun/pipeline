package com.sun.pipeline.stock.domain;

import com.sun.pipeline.stock.Contants;
import com.sun.pipeline.stock.explorer.sohu.SohuStockHttpCommandService;
import com.sun.pipeline.util.internal.http.HttpGet;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by zksun on 2017/9/21.
 */
public class ExcludeRightsWrapperTest {

    @Test
    public void excludeRightsTest() {
        SohuStockHttpCommandService instance = SohuStockHttpCommandService.getInstance();
        //HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        List<ExcludeRights> excludeRightses = instance.getExcludeRightsInfo( Contants.DEFAULT_SOHU_INFO_HTTP_GET, "sh600004");

        ExcludeRightsWrapper rightsWrapper = ExcludeRightsWrapper.getInstance("sh600004",excludeRightses);

        long l = rightsWrapper.calculateAdjustStockPrice(LocalDate.of(2017, 4, 27), 1619);
        System.out.println(l);
    }

}
