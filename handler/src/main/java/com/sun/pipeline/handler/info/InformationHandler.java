package com.sun.pipeline.handler.info;

/**
 * Created by zksun on 07/06/2017.
 */
public interface InformationHandler<R, V> {
    String getName();

    R getInformation(V value);
}
