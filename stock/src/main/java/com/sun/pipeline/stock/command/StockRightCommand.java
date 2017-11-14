package com.sun.pipeline.stock.command;

import com.sun.pipeline.mybatis.dao.StockRightDAO;
import com.sun.pipeline.mybatis.domain.StockRightDO;
import com.sun.pipeline.stock.Command;
import com.sun.pipeline.stock.domain.ExcludeRights;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * Created by zhikunsun on 2017/11/14.
 */
public class StockRightCommand implements Command {

    private final List<ExcludeRights> excludeRights;
    private final StockRightDAO stockRightDAO;

    @Override
    public int commandId() {
        return 200;
    }

    @Override
    public String commandName() {
        return "stockRight";
    }

    public StockRightCommand(List<ExcludeRights> excludeRights, StockRightDAO stockRightDAO) {
        this.excludeRights = excludeRights;
        this.stockRightDAO = stockRightDAO;
    }

    @Override
    public boolean doCommand() throws Exception {
        List<StockRightDO> rightDOs = convert(excludeRights);
        if (rightDOs.isEmpty()) {
            return false;
        }
        for (StockRightDO rightDO : rightDOs) {
            boolean b = hasInjected(rightDO.getStockCode(), rightDO.getAdjustDay());
            if (b) {
                stockRightDAO.update(rightDO);
            } else {
                stockRightDAO.insert(rightDO);
            }
        }
        return true;
    }

    private StockRightDO convert(ExcludeRights excludeRights) {
        StockRightDO stockRightDO = new StockRightDO();
        stockRightDO.setStockCode(excludeRights.getStockCode());
        stockRightDO.setExchangeStock(excludeRights.getExchangeStock());
        stockRightDO.setDistribute(excludeRights.getDistribute());
        stockRightDO.setAllotmentStock(excludeRights.getAllotmentStock());
        stockRightDO.setAllotmentPrice(excludeRights.getAllotmentPrice());
        stockRightDO.setAdjustDay(convert(excludeRights.getAdjustDay()));
        return stockRightDO;
    }

    private List<StockRightDO> convert(List<ExcludeRights> excludeRights) {
        List<StockRightDO> list = new ArrayList<>();
        for (ExcludeRights rights : excludeRights) {
            list.add(convert(rights));
        }
        return list;
    }

    private Date convert(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private boolean hasInjected(String code, Date date) {
        Map<String, Object> map = new HashMap<>();
        map.put("stockCode", code);
        map.put("adjustDay", date);
        int count = stockRightDAO.count(map);
        return count > 0 ? true : false;
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
