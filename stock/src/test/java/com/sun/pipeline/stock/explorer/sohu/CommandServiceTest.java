package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.stock.StockKlineStep;
import com.sun.pipeline.stock.Time;
import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.stock.domain.KlineItem;
import com.sun.pipeline.util.internal.http.HttpGet;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by zksun on 2017/9/20.
 */
public class CommandServiceTest {
    @Test
    public void calculateAllotmentPrice() {
        SohuStockHttpCommandService instance = SohuStockHttpCommandService.getInstance();
        HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        Long sh601688 = instance.calculateAllotmentPrice("sh_601688", LocalDate.of(2017,9,19), httpGetInstance);
        System.out.println(sh601688);
    }

    @Test
    public void getExcludeRightsInfo(){
        SohuStockHttpCommandService instance = SohuStockHttpCommandService.getInstance();
        HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        List<ExcludeRights> sh_601688 = instance.getExcludeRightsInfo(httpGetInstance, "sz_000776");
        System.out.println(sh_601688);
    }

    @Test
    public void getKlineInfo(){
        SohuStockHttpCommandService instance = SohuStockHttpCommandService.getInstance();
        HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        List<KlineItem> sz_000776 = instance.getKlineInfo(httpGetInstance, "000651", StockKlineStep.DAY, true,LocalDate.of(2017,9,20), -21);
        System.out.println(sz_000776);
    }


}
