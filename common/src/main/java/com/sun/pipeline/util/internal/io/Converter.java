package com.sun.pipeline.util.internal.io;

/**
 * Created by zksun on 08/06/2017.
 */
public interface Converter<T, S> {
    T convert(S source);
}
