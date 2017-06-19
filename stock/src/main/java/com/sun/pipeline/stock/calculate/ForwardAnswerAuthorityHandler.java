package com.sun.pipeline.stock.calculate;

import com.sun.pipeline.handler.calculate.CalculateHandler;
import com.sun.pipeline.stock.domain.ExcludeRights;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by zksun on 19/06/2017.
 */
public class ForwardAnswerAuthorityHandler implements CalculateHandler<List<ExcludeRights>, Integer> {
    @Override
    public Integer calculate(List<ExcludeRights> source) {
        if (CollectionUtils.isEmpty(source)) {
            throw new NullPointerException("no exclude base data");
        }
        return null;
    }
}
