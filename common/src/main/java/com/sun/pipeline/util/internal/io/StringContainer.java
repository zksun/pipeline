package com.sun.pipeline.util.internal.io;

/**
 * Created by zksun on 08/06/2017.
 */
public abstract class StringContainer<D> extends ContainerAdapter<D, Object> {

    @Override
    public Container swallow(String s) {
        add(convert(s));
        return this;
    }

    protected abstract D convert(String s);


}
