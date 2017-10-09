package com.sun.pipeline.stock;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.domain.KlineItem;
import com.sun.pipeline.stock.infomation.sohu.SohuForwardAnswerAuthorityInformationHandler;
import com.sun.pipeline.stock.infomation.sohu.SohuKlineInformationHandler;
import com.sun.pipeline.util.internal.http.HttpGet;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Created by zksun on 13/06/2017.
 */
public final class Contants {

    public final static String SOHU_STOCK_MAIN_PATH = "http://q.stock.sohu.com";
    public final static String SOHU_INFO_PATH = "/qp/hq";

    public final static HttpGet DEFAULT_SOHU_INFO_HTTP_GET = HttpGet.getHttpGetInstance(SOHU_STOCK_MAIN_PATH, 80, SOHU_INFO_PATH, 3);

    public final static SohuKlineInformationHandler kLineInformationHandler = new SohuKlineInformationHandler();
    public final static SohuForwardAnswerAuthorityInformationHandler excludeRightsHandler = new SohuForwardAnswerAuthorityInformationHandler();

    private Contants() {
    }
}
