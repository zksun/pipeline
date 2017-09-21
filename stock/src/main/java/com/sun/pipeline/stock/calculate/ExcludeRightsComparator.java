package com.sun.pipeline.stock.calculate;

import com.sun.pipeline.stock.domain.ExcludeRights;

import java.util.Comparator;

/**
 * Created by zksun on 2017/9/21.
 */
public final class ExcludeRightsComparator implements Comparator<ExcludeRights> {

    private final static ExcludeRightsComparator instance = new ExcludeRightsComparator();

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

    public static ExcludeRightsComparator getInstance() {
        return instance;
    }
}
