package com.sun.pipeline.util;

/**
 * Created by zksun on 15/05/2017.
 */
public interface Constant<T extends Constant<T>> extends Comparable<T> {

    int id();

    String name();
}
