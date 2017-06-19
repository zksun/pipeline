package com.sun.pipeline.handler.calculate;

/**
 * Created by zlsun on 19/06/2017.
 */
public interface CalculateHandler<S, R> {
    R calculate(S source);
}
