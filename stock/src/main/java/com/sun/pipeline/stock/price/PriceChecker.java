package com.sun.pipeline.stock.price;

import com.sun.pipeline.util.Checker;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by zksun on 13/06/2017.
 */
public class PriceChecker implements Checker<Integer> {

    public boolean isPenny(List<Integer> prices) {
        if (CollectionUtils.isEmpty(prices)) {
            throw new NullPointerException("no check prices");
        }
        for (Integer price : prices) {
            if (check(price)) {
                return false;
            }
        }
        return true;
    }

    public boolean check(Integer price) {
        if (null == price) {
            throw new NullPointerException("price");
        }

        if (price % 10 != 0) {
            return false;
        }

        return true;
    }
}
