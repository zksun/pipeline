package com.sun.pipeline.stock.explorer.sohu;

import com.sun.pipeline.stock.StockKlineStep;
import com.sun.pipeline.stock.Time;
import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.stock.domain.KlineItem;
import com.sun.pipeline.util.internal.http.HttpGet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static com.sun.pipeline.stock.Contants.excludeRightsHandler;
import static com.sun.pipeline.stock.Contants.kLineInformationHandler;
import static com.sun.pipeline.stock.StockUtil.calculateAveragePrice;
import static com.sun.pipeline.stock.StockUtil.getRealStockCode;

/**
 * Created by zksun on 2017/6/29.
 */
public final class SohuStockHttpCommandService {

    private static SohuStockHttpCommandService sohuStockHttpCommandService;

    private String DEFAULT_LOCAL = "cn";
    private String INFO_TYPE_PARAM = "type";
    private String INFO_TRADEDETAIL_VALUE = "tradeddetail";
    private String INFO_KLINE_VALUE = "kline";
    private String INFO_CODE_PARAM = "code";
    private String INFO_SET_PARAM = "set";
    private String INFO_COUNT_PARAM = "count";
    private String INFO_PERIOD_PARAM = "period";
    private String INFO_ADJUST_PARAM = "dr";
    private String INFO_STAR_DAY_PARAM = "date";
    private String DEFAULT_ADJUST = "0";
    private String ADJUST = "";

    public static SohuStockHttpCommandService getInstance() {
        if (null == sohuStockHttpCommandService) {
            sohuStockHttpCommandService = new SohuStockHttpCommandService();
        }
        return sohuStockHttpCommandService;
    }

    public Long calculateAllotmentPrice(String stockCode, LocalDate date, HttpGet httpGet) {
        if (StringUtils.isBlank(stockCode)) {
            throw new NullPointerException("stock code is null");
        }
        if (null == date) {
            throw new NullPointerException("date is necessary");
        }
        if (null == httpGet) {
            throw new NullPointerException("httpGet is necessary");
        }

        List<KlineItem> klineInfo = getKlineInfo(httpGet, stockCode, StockKlineStep.DAY, false, date, -21);
        if (klineInfo.size() > 0) {
            klineInfo.remove(0);
            return calculateAveragePrice(klineInfo);
        }

        return -1L;

    }


    public List<KlineItem> getKlineInfo(HttpGet httpGet, String stockCode, Time time, boolean adjust, LocalDate date, int count) {
        if (null == httpGet) {
            throw new NullPointerException("httpGet is necessary");
        }
        if (StringUtils.isBlank(stockCode)) {
            throw new NullPointerException("stock code");
        }
        stockCode = getRealStockCode(stockCode);

        httpGet.addParameters(INFO_TYPE_PARAM, INFO_KLINE_VALUE)
                .addParameters(INFO_CODE_PARAM, stockCode)
                .addParameters(INFO_SET_PARAM, DEFAULT_LOCAL)
                .addParameters(INFO_PERIOD_PARAM, time.getTime())
                .addParameters(INFO_ADJUST_PARAM, adjust == true ? ADJUST : DEFAULT_ADJUST);

        if (null != date) {
            DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
            httpGet = httpGet.addParameters(INFO_STAR_DAY_PARAM, yyyyMMdd.format(date))
                    .addParameters(INFO_COUNT_PARAM, String.valueOf(count));
        }

        List<KlineItem> response = null;
        try {
            response = httpGet.getResponseByStringParameter(kLineInformationHandler);
        } catch (IOException e) {
            // TODO: 2017/7/3 take log
        }

        if (CollectionUtils.isNotEmpty(response)) {
            return response;
        }
        return Collections.EMPTY_LIST;

    }

    public List<ExcludeRights> getExcludeRightsInfo(HttpGet httpGet, String stockCode) {
        if (null == httpGet) {
            throw new NullPointerException("http get is necessay");
        }
        if (StringUtils.isBlank(stockCode)) {
            throw new NullPointerException("stock code is necessary");
        }

        stockCode = getRealStockCode(stockCode);

        List<ExcludeRights> excludeRightses = null;
        try {
            excludeRightses = httpGet.addParameters(INFO_TYPE_PARAM, INFO_KLINE_VALUE)
                    .addParameters(INFO_CODE_PARAM, stockCode)
                    .addParameters(INFO_SET_PARAM, DEFAULT_LOCAL)
                    .addParameters(INFO_PERIOD_PARAM, StockKlineStep.DAY.getTime())
                    .addParameters(INFO_ADJUST_PARAM, DEFAULT_ADJUST).getResponseByStringParameter(excludeRightsHandler);
        } catch (IOException e) {
            // TODO: 2017/7/4 take log
        }


        if (CollectionUtils.isNotEmpty(excludeRightses)) {
            return excludeRightses;
        }

        return Collections.EMPTY_LIST;
    }


}
