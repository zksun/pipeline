package com.sun.pipeline.stock.infomation.sohu;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.pipeline.handler.info.InformationHandler;
import com.sun.pipeline.stock.domain.KlineItem;
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
 * Created by zksun on 2017/6/29.
 */
public class SohuKlineInformationHandler implements InformationHandler<List<KlineItem>, String> {

    @Override
    public String getName() {
        return SohuKlineInformationHandler.class.getCanonicalName();
    }

    @Override
    public List<KlineItem> getInformation(String value) {
        if (StringUtils.isBlank(value)) {
            throw new NullPointerException("");
        }
        Gson gson = new Gson();
        Map<String, Object> jsonObj = gson.fromJson(value, new TypeToken<Map<String, Object>>() {
        }.getType());

        String stockCode = (String) jsonObj.get("code");
        List<List<String>> dataList = (List<List<String>>) jsonObj.get("kline");

        if (CollectionUtils.isNotEmpty(dataList) && StringUtils.isNoneBlank(stockCode)) {
            return getData(dataList, stockCode);
        }

        return Collections.emptyList();
    }

    private List<KlineItem> getData(List<List<String>> dataList, String stockCode) {
        List<KlineItem> klineItems = new ArrayList<>(dataList.size());
        for (List<String> list : dataList) {
            KlineItem klineItem = new KlineItem();
            klineItem.setStockCode(stockCode);
            String[] strings = list.toArray(new String[list.size()]);
            try {
                klineItem.setDate(LocalDate.parse(strings[0].trim(), DateTimeFormatter.ofPattern("yyyyMMdd")));
            } catch (Exception e) {
                //igore
            }
            klineItem.setOpenPrice(new Double(NumberUtils.toDouble(strings[1].trim()) * 100D).longValue());
            klineItem.setClosePrice(new Double(NumberUtils.toDouble(strings[2].trim()) * 100D).longValue());
            klineItem.setHighPrice(new Double(NumberUtils.toDouble(strings[3].trim()) * 100D).longValue());
            klineItem.setLowPrice(new Double(NumberUtils.toDouble(strings[4].trim()) * 100D).longValue());
            klineItem.setTradedVolume(new Double(NumberUtils.toDouble(strings[5].trim())).longValue());
            klineItem.setTradedAmount(new Double(NumberUtils.toDouble(strings[6].trim()) * 100D).longValue());
            klineItem.setHandRate(new Double(NumberUtils.toDouble(strings[7].trim())));
            klineItem.setUpDown(new Double(NumberUtils.toDouble(strings[8].trim()) * 100D).longValue());
            klineItem.setUpDownRatio(new Double(NumberUtils.toDouble(strings[9].trim())));
            klineItems.add(klineItem);
        }
        return klineItems;
    }
}
