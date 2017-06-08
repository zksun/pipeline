package com.sun.pipeline.util.internal.io;

import java.io.InputStream;

/**
 * Created by zksun on 08/06/2017.
 */
public abstract class InputStreamContainer<D> extends ContainerAdapter<D, Object> {

    @Override
    public Container swallow(InputStream inputStream) {
        add(convert(inputStream));
        return this;
    }

    abstract D convert(InputStream inputStream);
}
