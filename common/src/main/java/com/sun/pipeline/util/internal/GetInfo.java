package com.sun.pipeline.util.internal;

import com.sun.pipeline.handler.info.InformationHandler;

import java.io.IOException;

/**
 * Created by zksun on 07/06/2017.
 */
public interface GetInfo {

    <R> R getResponse() throws IOException;

    <R> R getResponseByByteArrayParameter(InformationHandler<R, byte[]> informationHandler) throws IOException;

    <R> R getResponseByStringParameter(InformationHandler<R, String> informationHandler) throws IOException;

}
