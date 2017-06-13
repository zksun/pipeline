package com.sun.pipeline.util.cache;

/**
 * Created by zksun on 13/06/2017.
 */
public interface Cache<K, V> {

    V getValue(K key);

    void putValue(K key);

    boolean contain(K key);
}
