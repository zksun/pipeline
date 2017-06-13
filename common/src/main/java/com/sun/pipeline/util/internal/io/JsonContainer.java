package com.sun.pipeline.util.internal.io;

import java.util.List;

/**
 * Created by zksun on 13/06/2017
 */
public class JsonContainer<D, L extends List<D>> extends StringContainer<L> {

    private List<D> jsons;

    @Override
    L convert(String s) {
        return null;
    }

    @Override
    protected void add(L data) {
        for (D d : data) {
            jsons.add(d);
        }
    }

    @Override
    public L getData() {
        return (L) jsons;
    }
}
