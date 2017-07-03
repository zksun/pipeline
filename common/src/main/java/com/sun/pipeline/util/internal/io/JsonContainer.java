package com.sun.pipeline.util.internal.io;

import com.google.gson.Gson;
import com.sun.pipeline.util.reflect.TypeParameterTypeFinder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zksun on 13/06/2017
 */
public abstract class JsonContainer<D, R extends List<D>> extends StringContainer<D> {

    private R jsons;

    private Class _self;

    public JsonContainer() {
        this._self = TypeParameterTypeFinder.getTypeMatcherFinder().find(this, JsonContainer.class, "D");
    }

    @Override
    protected D convert(String s) {
        if (StringUtils.isBlank(s)) {
            throw new NullPointerException("json string is null");
        }
        Gson gson = new Gson();
        return (D) gson.fromJson(s, _self);
    }

    @Override
    protected void add(D data) {

        if (null == data) {
            throw new NullPointerException("json string is null");
        }
        synchronized (this) {
            if (null == jsons) {
                jsons = (R) new ArrayList();
            }
            jsons.add(data);
        }
    }

    public R getData() {
        return jsons;
    }

}
