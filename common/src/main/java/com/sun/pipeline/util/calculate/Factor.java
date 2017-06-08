package com.sun.pipeline.util.calculate;

/**
 * Created by zksun on 08/06/2017.
 */
public interface Factor<R> {
    long id();

    R value();

    default Factor<R> merge(Factor f) {
        return this.merge(f);
    }
}
