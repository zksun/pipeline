package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.domain.Tube;

import java.util.*;

/**
 * Created by zhikunsun on 17/9/25.
 */
public class PriceTube implements Tube<StockPrice> {

    private Map<String, DistributedTube> hashMap = new HashMap<>();

    @Override
    public void add(StockPrice stockPrice) {
        if (hashMap.containsKey(stockPrice.getAuthorityPrice().toString())) {
            DistributedTube distributedTube = hashMap.get(stockPrice.getAuthorityPrice().toString());
            distributedTube.add(stockPrice);
            return;
        }
        DistributedTube distributedTube = new DistributedTube();
        distributedTube.add(stockPrice);
        hashMap.put(stockPrice.getAuthorityPrice().toString(), distributedTube);
    }

    public class DistributedTube implements Tube<StockPrice> {
        private Long total = 0L;
        private Long price;

        @Override
        public void add(StockPrice stockPrice) {
            price = stockPrice.getAuthorityPrice();
            total = total + stockPrice.getAuthorityPrice() * stockPrice.getHand();
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        public Long getPrice() {
            return price;
        }

        public void setPrice(Long price) {
            this.price = price;
        }
    }

    public List<DistributedTube> getDistributed() {
        if (hashMap.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Collection<DistributedTube> values = hashMap.values();
        ArrayList arrayList = new ArrayList(values.size());
        for (DistributedTube tube : values) {
            arrayList.add(tube);
        }
        arrayList.sort(new DistributedComparator());
        return arrayList;
    }


    private static class DistributedComparator implements Comparator<DistributedTube> {

        @Override
        public int compare(DistributedTube o1, DistributedTube o2) {
            if (o1.total > o2.total) {
                return -1;
            } else if (o1.total < o2.total) {
                return 1;
            }
            return 0;
        }
    }
}
