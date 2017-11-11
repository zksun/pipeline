package com.sun.pipeline.mybatis;

import com.sun.pipeline.mybatis.dao.StockBaseDAO;
import com.sun.pipeline.mybatis.domain.StockBaseDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by zhikunsun on 2017/11/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DatabaseDAOTest {
    @Resource
    private StockBaseDAO stockBaseDAO;

    @Test
    public void queryTest() {
        StockBaseDO stockBaseDO = stockBaseDAO.queryById(3);
        System.out.println(stockBaseDO);
    }

    @Test
    public void insertTest() {
        StockBaseDO stockBaseDO = new StockBaseDO();
        stockBaseDO.setCode(600010);
        stockBaseDO.setExright(10000L);
        stockBaseDO.setPrice(10002L);
        stockBaseDO.setHand(100);
        stockBaseDO.setTime(new Date());
        stockBaseDO.setWish((byte) 1);
        stockBaseDAO.insert(stockBaseDO);
        System.out.println("end");
    }

}
