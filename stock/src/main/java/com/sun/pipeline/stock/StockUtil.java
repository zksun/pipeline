package com.sun.pipeline.stock;

import com.sun.pipeline.stock.domain.KlineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zksun on 19/06/2017.
 */
public final class StockUtil {

    public static int getRealSequence(int source) {
        if (source < 780) {
            return source - 570;
        } else {
            return source - 660;
        }
    }

    public static String getRealStockCode(String code) {
        if (null == code || code.equals("")) {
            throw new NullPointerException("code");
        }
        Matcher matcher = Pattern.compile("(sz|sh)(\\w+)").matcher(code);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }

    public static Long calculateAveragePrice(List<KlineItem> klineItems) {
        long total = 0;
        for (KlineItem item : klineItems) {
            total += item.getClosePrice();
        }
        BigDecimal bigDecimal = new BigDecimal(total);
        return bigDecimal.divide(new BigDecimal(klineItems.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(.7)).longValue();
    }

    private StockUtil() {
    }
}
