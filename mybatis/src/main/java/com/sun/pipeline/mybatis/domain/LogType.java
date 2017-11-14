package com.sun.pipeline.mybatis.domain;

/**
 * Created by zhikunsun on 2017/11/14.
 */
public enum LogType {
    INJECTBASEDATA(1);
    int type;

    LogType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
