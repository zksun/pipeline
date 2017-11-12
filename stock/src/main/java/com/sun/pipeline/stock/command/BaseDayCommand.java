package com.sun.pipeline.stock.command;

import com.sun.pipeline.mybatis.dao.StockBaseDAO;
import com.sun.pipeline.mybatis.domain.StockBaseDO;
import com.sun.pipeline.stock.Command;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.domain.Trade;
import com.sun.pipeline.stock.price.StockDayContainer;
import com.sun.pipeline.stock.service.InjectDataService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class BaseDayCommand implements Command {

    private StockBaseDAO stockBaseDAO;

    private final StockDayContainer stockDayContainer;

    public BaseDayCommand(StockDayContainer container, StockBaseDAO dao) {
        this.stockDayContainer = container;
        this.stockBaseDAO = dao;
    }

    @Override
    public int commandId() {
        return 100;
    }

    @Override
    public String commandName() {
        return "baseDay";
    }

    @Override
    public boolean doCommand() throws Exception {
        List<StockPrice> data = stockDayContainer.getData();
        if (null != data && !data.isEmpty()) {
            List<StockBaseDO> stockBaseDOs = new ArrayList<>();
            for (StockPrice price : data) {
                StockBaseDO stockBaseDO = new StockBaseDO();
                stockBaseDO.setPrice(price.getPrice());
                stockBaseDO.setExright(price.getAuthorityPrice());
                stockBaseDO.setWish((byte) price.getTrade().getCode());
                stockBaseDO.setTime(convert(price.getTime().getDateTime()));
                stockBaseDO.setHand(price.getHand());
                stockBaseDO.setCode(price.getStock().getStockCode());
                stockBaseDOs.add(stockBaseDO);
            }
            stockBaseDAO.batchInsert(stockBaseDOs);
            return true;
        }
        return false;
    }

    private Date convert(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    @Override
    public void run() {
        try {
            doCommand();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
