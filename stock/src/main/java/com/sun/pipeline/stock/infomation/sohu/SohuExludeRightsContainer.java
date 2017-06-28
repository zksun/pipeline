package com.sun.pipeline.stock.infomation.sohu;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.util.internal.io.StringContainer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 2017/6/27.
 */
public class SohuExludeRightsContainer extends StringContainer<List<ExcludeRights>> {


    private List<ExcludeRights> data;

    @Override
    protected List<ExcludeRights> convert(String s) {
        if (StringUtils.isBlank(s)) {
            throw new NullPointerException("json string is null");
        }

        Gson gson = new Gson();
        Map<String, Object> jsonObj = gson.fromJson(s, new TypeToken<Map<String, Object>>() {
        }.getType());

        String stockCode = (String) jsonObj.get("code");
        List<List<String>> dataList = (List<List<String>>) jsonObj.get("div");
        return convert(dataList, stockCode);
    }

    @Override
    protected synchronized void add(List<ExcludeRights> data) {
        if (null != data) {
            throw new UnsupportedOperationException();
        }
        this.data = data;
    }

    @Override
    protected List<ExcludeRights> getData() {
        return this.data;
    }

    private List<ExcludeRights> convert(List<List<String>> dataList, String stockCode) {
        if (StringUtils.isBlank(stockCode) || CollectionUtils.isEmpty(dataList)) {
            throw new NullPointerException("no data");
        }
        List<ExcludeRights> excludeRightists = new ArrayList<>(dataList.size());
        for (List<String> data : dataList) {
            ExcludeRights excludeRights = new ExcludeRights(stockCode);
            String[] strings = data.toArray(new String[data.size()]);
            if (strings.length != 4) {
                throw new IllegalArgumentException();
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate adjustDay = LocalDate.parse(strings[0].trim(), formatter);
            excludeRights.setAdjustDay(adjustDay);
            excludeRights.setAllotmentStock(NumberUtils.toInt(strings[1].trim()));
            if (excludeRights.getAllotmentPrice() > 0) {
                //计算一个21天平均价
            } else {
                excludeRights.setAllotmentPrice(0L);
            }
            excludeRights.setDistribute(new Double(NumberUtils.toDouble(strings[2].trim()) * 100D).longValue());
            excludeRights.setExchangeStock(new Double(NumberUtils.toDouble(strings[3].trim())).intValue());
            excludeRightists.add(excludeRights);
        }
        return excludeRightists;

    }


}
