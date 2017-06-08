package com.sun.pipeline.util.calculate;

/**
 * Created by zksun on 08/06/2017.
 */
public interface Factor<R> {
    /**
     * 算子的唯一id号
     *
     * @return long型的唯一号
     */
    long id();

    /**
     * 算子的当前值
     *
     * @return
     */
    R value();

    /**
     * @param factor
     * @return
     */
    Factor<R> merge(Factor factor);

    /**
     * 算子中是不是有值
     * @return
     */
    boolean hasValue();

    /**
     * 算子的当前状态
     * @return
     */
    int status();


}
