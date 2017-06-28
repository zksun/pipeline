package com.sun.pipeline.stock.infomation.sohu;

import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.domain.ExcludeRights;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by zksun on 19/06/2017.
 */
public class SohuForwardAnswerAuthorityInformationHandler implements InformationHandler<List<ExcludeRights>, String> {

    @Override
    public String getName() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public List<ExcludeRights> getInformation(String value) {
        if (StringUtils.isBlank(value)) {
            throw new NullPointerException("");
        }

        return Collections.emptyList();
    }
}
