package com.sun.pipeline.stock.command;

import com.sun.pipeline.mybatis.dao.StockDayCountDAO;
import com.sun.pipeline.mybatis.domain.StockDayCountDO;
import com.sun.pipeline.mybatis.domain.StockRightDO;
import com.sun.pipeline.stock.Command;
import com.sun.pipeline.stock.price.StockDayContainer;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhikunsun on 2017/11/14.
 */
public class StockDayCountCommand implements Command {

    private final StockDayCountDAO stockDayCountDAO;

    private final StockDayContainer stockDayContainer;

    public StockDayCountCommand(StockDayContainer stockDayContainer, StockDayCountDAO stockDayCountDAO) {
        this.stockDayContainer = stockDayContainer;
        this.stockDayCountDAO = stockDayCountDAO;
    }

    @Override
    public int commandId() {
        return 300;
    }

    @Override
    public String commandName() {
        return "stockDayCountCommand";
    }

    @Override
    public boolean doCommand() throws Exception {
        String stockCode = stockDayContainer.getStock().getStockCode();
        int hand = stockDayContainer.totalHand();
        long avgPrice = stockDayContainer.avgPrice();
        Date date = convert(stockDayContainer.getDateTime());
        StockDayCountDO stockDayCountDO = new StockDayCountDO();
        stockDayCountDO.setAvg(avgPrice);
        stockDayCountDO.setTotalHand(hand);
        stockDayCountDO.setCode(stockCode);
        stockDayCountDO.setDay(date);
        stockDayCountDAO.insert(stockDayCountDO);
        return true;
    }


    private Date convert(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
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
