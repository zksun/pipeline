package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.util.internal.io.StringContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zksun on 13/06/2017.
 */
public class StockContainer extends StringContainer<StockPrice> {

    private List<StockPrice> prices;

    protected void add(StockPrice data) {
        synchronized (this) {
            if (null == prices) {
                this.prices = new ArrayList<StockPrice>();
            }
            prices.add(data);
        }
    }

    public StockPrice getData() {
        return null;
    }

    protected StockPrice convert(String s) {

        return null;
    }
}
