package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.mybatis.dao.StockRightDAO;
import com.sun.pipeline.mybatis.domain.StockRightDO;
import com.sun.pipeline.stock.Contants;
import com.sun.pipeline.stock.domain.ExcludeRights;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 2017/9/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SohuStockHttpCommandServiceTest {

    @Resource
    private StockRightDAO stockRightDAO;

    @Test
    public void queryStockExcludeRightsTest() {
        SohuStockHttpCommandService instance = SohuStockHttpCommandService.getInstance();
        //HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        List<ExcludeRights> excludeRightses = instance.getExcludeRightsInfo(Contants.DEFAULT_SOHU_INFO_HTTP_GET, "sh600004");
        System.out.println(excludeRightses);
        Map<String, Object> map = new HashMap<>();
        map.put("stockCode", "cn600004");
        List<StockRightDO> query = stockRightDAO.query(map);
        System.out.println(query);
    }
}
