package com.sun.pipeline.stock.explorer;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.util.internal.http.AbstractHttpGet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zksun on 19/06/2017.
 */
public abstract class AbstractStockExplorerContext<V> implements ExplorerContext<AbstractHttpGet, V> {

    private Map<String, InformationHandler> handlerMap = new HashMap<>();

    final static String GET_KLINE_INFO = "GET_KLINE_INFO";

    private AbstractHttpGet httpGet;

    @Override
    public ExplorerContext fireGetInfo(AbstractHttpGet get) {
        if (null == get) {
            throw new NullPointerException("no info get");
        }
        this.httpGet = get;
        fireInformationHandler(fireHttpGetInformationName());
        return this;
    }

    @Override
    public void registerInformationHandler(InformationHandler informationHandler) {
        synchronized (handlerMap) {
            if (handlerMap.containsKey(informationHandler.getName())) {
                return;
            }
            handlerMap.put(informationHandler.getName(), informationHandler);
        }
    }

    protected abstract String fireHttpGetInformationName();


}
