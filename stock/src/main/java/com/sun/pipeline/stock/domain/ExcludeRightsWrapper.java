package com.sun.pipeline.stock.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

/**
 * Created by zksun on 2017/9/21.
 */
public class ExcludeRightsWrapper {

    private final List<ExcludeRights> excludeRightses;

    public ExcludeRightsWrapper(List<ExcludeRights> list) {
        if (null == list) {
            throw new NullPointerException("list empty");
        }
        this.excludeRightses = list;
        sortList();
    }

    private void sortList() {
        sort(excludeRightses, new ExcludeRightsComparator());
    }

    public List<ExcludeRights> getExcludeRightses() {
        return excludeRightses;
    }

    public long calculateAdjustStockPrice(LocalDate tradeDate, long tradePrice) {
        if (null == tradeDate) {
            throw new NullPointerException();
        }

        if (tradePrice < 1) {
            throw new IllegalArgumentException("error price");
        }

        List<ExcludeRights> rightsList = getExcludeRightsByDate(tradeDate);

        if (rightsList.isEmpty()) {
            return tradePrice;
        }

        long adjustPrice = tradePrice;

        //System.out.println("get cur price is: " + tradePrice + " day is: " + tradeDate.toString());

        for (ExcludeRights rights : rightsList) {
            adjustPrice = exchangeAdjustStockPrice(adjustPrice, rights);
        }
        return adjustPrice;
    }

    private long exchangeAdjustStockPrice(long tradedPrice, ExcludeRights rights) {
        if (tradedPrice < 1) {
            throw new IllegalArgumentException("error price");
        }
        Long numerator = tradedPrice * 10L
                - rights.getDistribute()//每十股分红
                + rights.getAllotmentPrice()//每十股配股价
                * rights.getAllotmentStock();//每十股配股

        if (numerator < 0) {
            throw new IllegalArgumentException("error price");
        }

        Long denominator = 10L + rights.getExchangeStock() + rights.getAllotmentStock();

        return new Double(new BigDecimal(numerator)
                .divide(new BigDecimal(denominator), 2, BigDecimal.ROUND_HALF_UP)
                .doubleValue()).longValue();
    }

    private List<ExcludeRights> getExcludeRightsByDate(LocalDate date) {
        int index = 0;
        for (ExcludeRights rights : this.excludeRightses) {
            if (date.isBefore(rights.getAdjustDay())) {
                index = this.excludeRightses.size() - index;
                break;
            }
            index++;
        }
        if (index == this.excludeRightses.size()) {
            return Collections.EMPTY_LIST;
        }
        return copySubListByIndex(index);
    }

    private List<ExcludeRights> copySubListByIndex(int index) {
        return excludeRightses.subList(index, excludeRightses.size());
    }

    private static class ExcludeRightsComparator implements Comparator<ExcludeRights> {
        @Override
        public int compare(ExcludeRights o1, ExcludeRights o2) {
            if (o1.getAdjustDay().isAfter(o2.getAdjustDay())) {
                return 1;
            } else if (o1.getAdjustDay().isBefore(o2.getAdjustDay())) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
