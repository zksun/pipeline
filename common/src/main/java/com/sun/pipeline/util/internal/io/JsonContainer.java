package com.sun.pipeline.util.internal.io;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zksun on 13/06/2017
 */
public class JsonContainer<D, L extends List<D>> extends StringContainer<L> {

    private L jsons;

    @Override
    protected String convert(String s) {
        return null;
    }

    @Override
    protected void add(String data) {

        if (StringUtils.isBlank(data)) {
            throw new NullPointerException("json string is null");
        }
        synchronized (this) {
            if (null == jsons) {
                jsons = (L) new ArrayList<D>();
            }
            Gson gson = new Gson();
            //gson.fromJson(data,D.class);
            //jsons.add();
        }
    }

    @Override
    public L getData() {
        return null;
    }

}
