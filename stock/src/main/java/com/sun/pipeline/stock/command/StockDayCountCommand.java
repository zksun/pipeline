package com.sun.pipeline.stock.command;

import com.sun.pipeline.stock.Command;

/**
 * Created by zhikunsun on 2017/11/14.
 */
public class StockDayCountCommand implements Command {

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

        return false;
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
