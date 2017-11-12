package com.sun.pipeline.stock.command;

import com.sun.pipeline.mybatis.domain.StockBaseDO;
import com.sun.pipeline.stock.Command;
import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.price.StockDayContainer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class BaseDayCommand implements Command {

    private final StockDayContainer stockDayContainer;

    private BaseDayCommand(StockDayContainer container) {
        this.stockDayContainer = container;
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

            }
        }
        return false;
    }


}
