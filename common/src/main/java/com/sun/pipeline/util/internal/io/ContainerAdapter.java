package com.sun.pipeline.util.internal.io;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by zksun on 08/06/2017.
 */
public abstract class ContainerAdapter<D, I> implements Container<D, I> {

    protected D data;

    @Override
    public D getData() {
        return data;
    }

    @Override
    public Container swallow(Container container) {
        return this;
    }

    @Override
    public Container swallow(InputStream inputStream) {
        return this;
    }

    @Override
    public Container swallow(File file) {
        return null;
    }

    @Override
    public Container swallow(String s) {
        return this;
    }

    @Override
    public Container swallow(I input) {
        return this;
    }

    @Override
    public Container swallow(OutputStream outputStream) {
        return this;
    }

    protected abstract void add(D data);
}
