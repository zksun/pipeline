package com.sun.pipeline.stock;

import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.stock.domain.ExcludeRightsWrapper;
import com.sun.pipeline.stock.domain.KlineItem;
import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.explorer.sohu.SohuStockHttpCommandService;
import com.sun.pipeline.stock.price.StockDayContainer;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zksun on 19/06/2017.
 */
public final class StockUtil {

    /**
     * 得到交易自信的时间
     * @param source
     * @return
     */
    public static int getRealSequence(int source) {
        if (source < 780) {
            return source - 570;
        } else {
            return source - 660;
        }
    }

    /**
     * 通过股票代码得到股票文件
     * @param stockCode
     * @param stockDirectory
     * @return
     */
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

    /**
     * 计算复权价
     * @param stockCode
     * @param time
     * @param source
     * @param authority
     * @return
     */
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

    /**
     * 得到sh600001类似这样的股票代码的数字部分
     * @param code
     * @return
     */
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

    /**
     * 计算日平均价格
     * @param containers
     * @return
     */
    public static long getDayContainerAVGPrice(List<StockDayContainer> containers) {
        double total = 0;
        for (StockDayContainer container : containers) {
            total += container.avgPrice();
        }
        return (long) total / containers.size();
    }

    /**
     * 这是一个我都已经忘记的算法,看不懂是为了什么而写了,没有写注释,真他妈的蛋疼,不删除它,放在这里,让自己难受,这也好是一个教训
     * @param sellList
     * @param buyList
     * @return
     */
    public static List<StockPrice> compareSellBuyList(List<StockPrice> sellList, List<StockPrice> buyList) {

        int sellIndex = 0;
        int buyIndex = 0;
        int margin = 0;

        for (; ; ) {
            if (sellIndex == sellList.size() || buyIndex == buyList.size()) {
                break;
            }
            StockPrice sellPrice = sellList.get(sellIndex);
            StockPrice buyPrice = buyList.get(buyIndex);

            int sellHand = sellPrice.getHand() + margin;
            int buyHand = buyPrice.getHand();
            margin = sellHand - buyHand;

            if (margin > 0) {
                buyIndex++;
                margin = margin - sellHand;
            } else if (margin < 0) {
                sellIndex++;
                margin = margin + buyHand;
            } else {
                buyIndex++;
                sellIndex++;
            }
        }

        if (sellIndex < buyIndex) {
            return sellList.subList(sellIndex, sellList.size());
        } else if (sellIndex > buyIndex) {
            return buyList.subList(buyIndex, buyList.size());
        }

        return Collections.emptyList();
    }




    private StockUtil() {
    }
}
