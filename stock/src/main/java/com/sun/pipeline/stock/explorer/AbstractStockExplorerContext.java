package com.sun.pipeline.stock.explorer;

import com.sun.pipeline.util.GetInfo;
import com.sun.pipeline.util.internal.http.AbstractHttpGet;

/**
 * Created by zksun on 19/06/2017.
 */
public abstract class AbstractStockExplorerContext implements ExplorerContext {


    final static String GET_KLINE_INFO = "GET_KLINE_INFO";

    private AbstractHttpGet httpGet;

    @Override
    public ExplorerContext fireGetInfo(GetInfo get) {
        if (null == get) {
            throw new NullPointerException("no info get");
        }

        if (!(get instanceof AbstractHttpGet)) {
            throw new NullPointerException("now only support http get");
        }

        this.httpGet = (AbstractHttpGet) get;
        fireInformationHandler(fireHttpGetInformationName());
        return this;
    }

    protected abstract String fireHttpGetInformationName();


}
