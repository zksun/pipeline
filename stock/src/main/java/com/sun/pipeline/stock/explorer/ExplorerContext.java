package com.sun.pipeline.stock.explorer;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.util.GetInfo;

/**
 * Created by zksun on 19/06/2017.
 */
public interface ExplorerContext<G extends GetInfo, V> {

    ExplorerContext fireGetInfo(G get);

    ExplorerContext fireInformationHandler(String name);

    void registerInformationHandler(String name, InformationHandler informationHandler);

    V getValue();


}
