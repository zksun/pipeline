package com.sun.pipeline.handler.info;

/**
 * Created by zksun on 07/06/2017.
 */
public interface InformationHandler<R, V> {
    R getInformation(V value);
}
