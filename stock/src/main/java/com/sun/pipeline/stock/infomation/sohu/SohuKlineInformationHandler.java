package com.sun.pipeline.stock.infomation.sohu;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.domain.KlineItem;

import java.util.List;

/**
 * Created by zksun on 2017/6/29.
 */
public class SohuKlineInformationHandler implements InformationHandler<List<KlineItem>, String> {

    @Override
    public String getName() {
        return SohuKlineInformationHandler.class.getCanonicalName();
    }

    @Override
    public List<KlineItem> getInformation(String value) {
        return null;
    }
}
