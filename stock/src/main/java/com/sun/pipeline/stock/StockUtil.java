package com.sun.pipeline.stock;

import com.sun.pipeline.stock.domain.*;
import com.sun.pipeline.stock.explorer.sohu.SohuStockHttpCommandService;

import java.io.File;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.ObjectUtils.max;
import static org.apache.commons.lang3.ObjectUtils.min;

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

    public static File find(String stockCode, List<File> stockDirectory) {
        if (null == stockCode || stockCode.equals("")) {
            throw new NullPointerException("stock code");
        }
        if (null == stockDirectory || stockDirectory.isEmpty()) {
            throw new NullPointerException("stock directory");
        }

        for (File file : stockDirectory) {
            if (file.getName().equals(stockCode) && file.isDirectory()) {
                return file;
            }
        }
        return null;
    }

    public static long calculateAuthorityPrice(String stockCode, LocalDate time, long source, Authority authority) {
        switch (authority) {
            case FORWARD_ANSWER_AUTHORITY: {
                List<ExcludeRights> excludeRightsInfo = SohuStockHttpCommandService.getInstance()
                        .getExcludeRightsInfo(Contants.DEFAULT_SOHU_INFO_HTTP_GET, stockCode);
                if (null == excludeRightsInfo) {
                    throw new NullPointerException();
                }
                return ExcludeRightsWrapper.getInstance(stockCode, excludeRightsInfo).calculateAdjustStockPrice(time, source);
            }
            case BACKWARD_ANSWER_AUTHORITY:
                throw new UnsupportedOperationException();
            default:
                throw new IllegalArgumentException("unknown authority");
        }
    }

    public static String getRealStockCode(String code) {
        if (null == code || code.equals("")) {
            throw new NullPointerException("code");
        }
        Matcher matcher = Pattern.compile("(sz|sh)(\\d+)").matcher(code);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }

    public static LocalDate getRealTime(String code) {
        if (null == code || code.equals("")) {
            throw new NullPointerException("code");
        }
        Matcher matcher = Pattern.compile("(\\d+)(\\.txt)").matcher(code);
        if (matcher.find()) {
            return LocalDate.parse(matcher.group(1), DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        throw new IllegalArgumentException();
    }

    public static Long calculateAveragePrice(List<KlineItem> klineItems) {
        long total = 0;
        for (KlineItem item : klineItems) {
            total += item.getClosePrice();
        }
        BigDecimal bigDecimal = new BigDecimal(total);
        return bigDecimal.divide(new BigDecimal(klineItems.size()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(.7)).longValue();
    }

    public static byte[] writeExcludeRights(String stockCode, List<ExcludeRights> rightList) {
        if (null == rightList) {
            throw new NullPointerException("rightList");
        }

        if (!stockCode.matches("(sh|sz)(//d+)")) {
            throw new IllegalArgumentException("wrong stock code");
        }

        return null;
    }

    public static List compareSellBuyList(List<StockPrice> sellList, List<StockPrice> buyList) {
        int sellIndex = 0;
        int buyIndex = 0;

        int margin = 0;

        int len = max(sellList.size(), buyList.size());
        for (int i = 0; i < 2 * len; i++) {
            if (sellIndex == sellList.size() - 1 || buyIndex == buyList.size() - 1) {
                break;
            }
            StockPrice sellPrice = sellList.get(sellIndex);
            StockPrice buyPrice = buyList.get(buyIndex);

            int sellHand = sellPrice.getHand() + margin;
            int buyHand = buyPrice.getHand();
            margin = sellHand - buyHand;

            if (margin > 0) {
                buyIndex++;
                margin = 0 - margin;
            } else if (margin < 0) {
                sellIndex++;
            } else {
                buyIndex++;
                sellIndex++;
            }
        }


        return null;
    }

    private StockUtil() {
    }
}
