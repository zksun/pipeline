package com.sun.pipeline.stock.explorer;

/**
 * Created by zksun on 19/06/2017.
 */
public interface Explorer<R, C extends ExplorerContext> {
    R getInfo(C context);
}
