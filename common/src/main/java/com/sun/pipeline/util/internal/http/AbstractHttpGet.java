package com.sun.pipeline.util.internal.http;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.util.internal.GetInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by zksun on 07/06/2017.
 */
public abstract class AbstractHttpGet implements GetInfo {

    abstract <T> T getHttpClient();

    abstract String getUrl();

    abstract int getPort();

    abstract <T> List<T> getParameters();

    abstract AbstractHttpGet addParameters(String parameterName, String value);

    abstract AbstractHttpGet addPath(String path);

    abstract void setQueryString();

    abstract boolean isActive();

    abstract byte[] queryBytes();

    protected int parameterSize() {
        if (null == getParameters()) {
            return 0;
        }
        return getParameters().size();
    }

    public byte[] getResponse() throws IOException {
        if (!isActive()) {
            throw new IllegalStateException("httpClient does not active");
        }
        return queryBytes();
    }

    public <R> R getResponseByByteArrayParameter(InformationHandler<R, byte[]> informationHandler) throws IOException {
        if (null == informationHandler) {
            throw new NullPointerException("information handler");
        }
        return informationHandler.getInformation(getResponse());
    }

    public <R> R getResponseByStringParameter(InformationHandler<R, String> informationHandler) throws IOException {
        if (null == informationHandler) {
            throw new NullPointerException("information handler");
        }
        return informationHandler.getInformation(new String(getResponse()));
    }

}
