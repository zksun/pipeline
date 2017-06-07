package com.sun.pipeline.util.internal.http;

import com.sun.pipeline.util.internal.GetInfo;

import java.util.List;

/**
 * Created by zksun on 07/06/2017.
 */
public abstract class AbstractHttpGet implements GetInfo {

    private volatile boolean active = true;

    abstract <T> T getHttpClient();

    abstract String getUrl();

    abstract int getPort();

    abstract <T> List<T> getParameters();


}
