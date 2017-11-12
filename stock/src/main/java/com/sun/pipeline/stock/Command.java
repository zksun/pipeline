package com.sun.pipeline.stock;

/**
 * Created by zhikunsun on 17/9/24.
 */
public interface Command extends Runnable{
    int commandId();
    String commandName();
    boolean doCommand() throws Exception;
}
