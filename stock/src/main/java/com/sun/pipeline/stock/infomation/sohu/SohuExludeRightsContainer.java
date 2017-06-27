package com.sun.pipeline.stock.infomation.sohu;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.util.internal.io.JsonContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 2017/6/27.
 */
public class SohuExludeRightsContainer extends JsonContainer<ExcludeRights, ArrayList<ExcludeRights>> {

    @Override
    protected ExcludeRights convert(String s) {
        ExcludeRights excludeRights = new ExcludeRights();
        Map<String, Object> map = new Gson().fromJson(s, new TypeToken<Map<String, Object>>() {
        }.getType());
        String stockCode = (String) map.get("code");
        List<List<String>> dataList = (List<List<String>>) map.get("div");



        return excludeRights;
    }
}
