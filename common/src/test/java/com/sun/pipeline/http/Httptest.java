package com.sun.pipeline.http;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.pipeline.util.internal.http.HttpGet;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by zksun on 07/06/2017.
 */
public class HttpTest {

    private final static String DEFAULT_LOCAL = "cn";

    private final static String INFO_TYPE_PARAM = "type";

    private final static String INFO_TRADEDETAIL_VALUE = "tradedetail";

    private final static String INFO_KLINE_VALUE = "kline";

    private final static String INFO_CODE_PARAM = "code";

    private final static String INFO_SET_PARAM = "set";

    private final static String INFO_COUNT_PARAM = "count";

    private final static String INFO_PERIOD_PARAM = "period";

    private final static String INFO_ADJUST_PARAM = "dr";

    private final static String INFO_STAR_DAY_PARAM = "date";

    private final static String DEFAULT_ADJUST = "0";

    @Test
    public void HttpQueryTest() {
        HttpGet httpGetInstance = HttpGet.getHttpGetInstance("http://q.stock.sohu.com", 80, "/qp/hq", 3);
        httpGetInstance.addParameters(INFO_TYPE_PARAM, INFO_KLINE_VALUE)
                .addParameters(INFO_CODE_PARAM, "601688")
                .addParameters(INFO_SET_PARAM, DEFAULT_LOCAL)
                .addParameters(INFO_PERIOD_PARAM, "day").addParameters(INFO_ADJUST_PARAM, "0");
        try {
            byte[] response = httpGetInstance.getResponse();
            String adjust = new String(response);
            Map<String, Object> jsonObj = new Gson().fromJson(adjust, new TypeToken<Map<String, Object>>() {
            }.getType());

            System.out.println(jsonObj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
