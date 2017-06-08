package com.sun.pipeline.util.calculate;

/**
 * Created by zksun on 08/06/2017.
 */
public interface Computable<F extends Factor, R> {
    R calculation(F factor);
}
