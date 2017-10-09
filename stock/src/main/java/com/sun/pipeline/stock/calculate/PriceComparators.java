package com.sun.pipeline.stock.calculate;

import com.sun.pipeline.stock.domain.StockPrice;

import java.util.Comparator;

/**
 * Created by zhikunsun on 17/9/25.
 */
public interface PriceComparators {
    class PriceAscComparator implements Comparator<StockPrice> {
        @Override
        public int compare(StockPrice o1, StockPrice o2) {
            if (o1.getAuthorityPrice() > o2.getAuthorityPrice()) {
                return 1;
            } else if (o1.getAuthorityPrice() < o2.getAuthorityPrice()) {
                return -1;
            }
            return 0;
        }
    }

    class PriceDesComparator implements Comparator<StockPrice> {
        @Override
        public int compare(StockPrice o1, StockPrice o2) {
            if (o1.getAuthorityPrice() > o2.getAuthorityPrice()) {
                return -1;
            } else if (o1.getAuthorityPrice() < o2.getAuthorityPrice()) {
                return 1;
            }
            return 0;
        }
    }
}
