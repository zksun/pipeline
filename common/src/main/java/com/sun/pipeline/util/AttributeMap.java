package com.sun.pipeline.util;

/**
 * Created by zksun on 15/05/2017.
 */
public interface AttributeMap {
    <T> Attribute<T> attr(AttributeKey<T> key);

    <T> boolean hasAttr(AttributeKey<T> key);
}
