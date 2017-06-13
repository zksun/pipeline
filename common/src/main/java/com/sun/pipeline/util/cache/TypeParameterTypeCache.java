package com.sun.pipeline.util.cache;

import java.util.Map;

/**
 * Created by zksun on 13/06/2017.
 */
public class TypeParameterTypeCache implements Cache<Class<?>, Class<?>> {

    private Map<Class<?>, Class<?>> typeParameterTypeGetCache;

    private Map<Class<?>, Map<String, Class<?>>> typeParameterTypeFindCache;

    @Override
    public Class<?> getValue(Class<?> key) {
        return null;
    }

    @Override
    public void putValue(Class<?> key) {

    }

    @Override
    public boolean contain(Class<?> key) {
        return false;
    }
}
