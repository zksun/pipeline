package com.sun.pipeline.stock;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.domain.KlineItem;
import com.sun.pipeline.stock.infomation.sohu.SohuKlineInformationHandler;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Created by zksun on 13/06/2017.
 */
public final class Contants {

    public final static String SOHU_STOCK_MAIN_PATH = "http://q.stock.sohu.com";
    public final static String SOHU_INFO_PATH = "/qp/hq";


    public final static SohuKlineInformationHandler kLineInformationHandler = new SohuKlineInformationHandler();
    private Contants() {
    }
}
