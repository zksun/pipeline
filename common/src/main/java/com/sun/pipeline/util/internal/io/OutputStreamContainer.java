package com.sun.pipeline.util.internal.io;

import java.io.OutputStream;

/**
 * Created by zksun on 08/06/2017.
 */
public abstract class OutputStreamContainer<D> extends ContainerAdapter<D, Object> {

    @Override
    public Container swallow(OutputStream outputStream) {
        this.data = convert(outputStream);
        return this;
    }

    abstract D convert(OutputStream outputStream);
}
