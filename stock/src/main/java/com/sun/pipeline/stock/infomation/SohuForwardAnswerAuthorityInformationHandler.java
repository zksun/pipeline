package com.sun.pipeline.stock.infomation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.domain.ExcludeRights;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 19/06/2017.
 */
public class SohuForwardAnswerAuthorityInformationHandler implements InformationHandler<List<ExcludeRights>, String> {

    @Override
    public List<ExcludeRights> getInformation(String value) {
        if (StringUtils.isBlank(value)) {
            throw new NullPointerException("");
        }

        Map<String, Object> map = new Gson().fromJson(value, new TypeToken<Map<String, Object>>() {
        }.getType());

        String stockCode = (String) map.get("code");
        List<List<String>> dataList = (List<List<String>>) map.get("div");



        return Collections.emptyList();
    }



}
