package com.sun.pipeline.stock.infomation.sohu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.Contants;
import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.stock.explorer.sohu.SohuStockHttpCommandService;
import com.sun.pipeline.util.Constant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

        Gson gson = new Gson();
        Map<String, Object> jsonObj = gson.fromJson(value, new TypeToken<Map<String, Object>>() {
        }.getType());

        String stockCode = (String) jsonObj.get("code");
        List<List<String>> dataList = (List<List<String>>) jsonObj.get("div");

        if (CollectionUtils.isNotEmpty(dataList) && StringUtils.isNoneBlank(stockCode)) {
            return getData(dataList, stockCode);
        }

        return Collections.emptyList();
    }

    private List<ExcludeRights> getData(List<List<String>> dataList, String stockCode) {
        List<ExcludeRights> excludeRightists = new ArrayList<>(dataList.size());
        for (List<String> list : dataList) {
            ExcludeRights right = new ExcludeRights(stockCode);
            String[] strings = list.toArray(new String[list.size()]);
            LocalDate adjustDay = LocalDate.parse(strings[0].trim(), DateTimeFormatter.ofPattern("yyyyMMdd"));
            right.setAdjustDay(adjustDay);
            right.setAllotmentStock(NumberUtils.toInt(strings[1].trim()));
            if (right.getAllotmentStock() > 0) {
                Long allotmentPrice = SohuStockHttpCommandService.getInstance()
                        .calculateAllotmentPrice(stockCode, adjustDay, Contants.DEFAULT_SOHU_INFO_HTTP_GET);
                right.setAllotmentPrice(allotmentPrice);

            } else {
                right.setAllotmentPrice(0L);
            }
            right.setDistribute(new Double(NumberUtils.toDouble(strings[2].trim()) * 100D).longValue());
            right.setExchangeStock(new Double(NumberUtils.toDouble(strings[3].trim())).intValue());
            excludeRightists.add(right);
        }
        return excludeRightists;
    }
}
