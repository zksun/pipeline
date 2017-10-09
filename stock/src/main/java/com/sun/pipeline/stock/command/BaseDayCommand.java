package com.sun.pipeline.stock.command;

import com.sun.pipeline.stock.Command;

import java.time.LocalDate;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class BaseDayCommand implements Command {

    private LocalDate day;

    private String stockCode;

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
        return false;
    }



}
