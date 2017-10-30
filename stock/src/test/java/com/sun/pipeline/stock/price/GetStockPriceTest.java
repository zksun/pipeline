package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.calculate.PriceComparators;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.LimitQueue;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.sun.pipeline.stock.StockUtil.find;
import static com.sun.pipeline.stock.StockUtil.getRealTime;
import static java.lang.Math.abs;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class GetStockPriceTest {
    @Test
    public void getDayPriceTest() {

    }

    @Test
    public void defaultFileOperatorDirectory() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory();
        System.out.println(list);
    }

    @Test
    public void defaultFileFilterOperatorDirectory() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\w+)"));
        System.out.println(list);
    }

    @Test
    public void defaultFileOperator() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\w+)"));
        List<File> files = defaultFileOperator.allFiles(list.get(0));
        System.out.println(files);
    }

    @Test
    public void defaultFileFilterOperator() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\w+)"));
        List<File> files = defaultFileOperator.allFiles(list.get(0), ((dir, name) -> name.matches("(\\w+)(\\.txt)")));
        System.out.println(files);
    }

    @Test
    public void stockDayContainer() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\w+)"));
        List<File> files = defaultFileOperator.allFiles(list.get(0), ((dir, name) -> name.matches("(\\w+)(\\.txt)")));
        Stock stock = new Stock(list.get(0).getName());
        StockDayContainer stockDayContainer = new StockDayContainer(stock, LocalDate.of(2017, 2, 3));
        files.forEach(file -> stockDayContainer.swallow(file));
        System.out.println(stockDayContainer);
    }

    @Test
    public void allStockDayContainers() {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\d+)"));
        for (File directory : list) {
            List<File> files = defaultFileOperator.allFiles(directory, ((dir, name) -> name.matches("(\\d+)(\\.txt)")));
            for (File file : files) {
                try {
                    Stock stock = new Stock(directory.getName());
                    StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(file.getName()));
                    stockDayContainer.swallow(file);
                    containers.add(stockDayContainer);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println(containers);
    }

    @Test
    public void oneStockDayContainers() {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\d+)"));
        String stockCode = "sz300498";
        File directory = find(stockCode, list);
        if (null != directory) {
            List<File> files = defaultFileOperator.allFiles(directory, ((dir, name) -> name.matches("(\\d+)(\\.txt)")));
            for (File file : files) {
                Stock stock = new Stock(directory.getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(file.getName()));
                stockDayContainer.swallow(file);
                containers.add(stockDayContainer);
            }
            List<StockPrice> buyList = new LinkedList<>();
            List<StockPrice> sellList = new LinkedList<>();
            List<StockPrice> priceList = new LinkedList<>();
            for (StockDayContainer container : containers) {
                List<StockPrice> data = container.getData();
                priceList.addAll(data);
//                for (StockPrice price : data) {
//                    if (price.getTrade().equals(Trade.BUY)) {
//                        buyList.add(price);
//                    } else if (price.getTrade().equals(Trade.SELL)) {
//                        sellList.add(price);
//                    }
//                }
            }
//            buyList.sort(new PriceComparators.PriceAscComparator());
//            sellList.sort(new PriceComparators.PriceDesComparator());
//
//            List<StockPrice> difference = compareSellBuyList(sellList, buyList);
            priceList.sort(new PriceComparators.PriceDesComparator());
            PriceTube priceTube = new PriceTube();
            for (StockPrice price : priceList) {
                priceTube.add(price);
            }
            Long hughAllPrice = priceTube.getHughAllPrice();
            List<PriceTube.DistributedTube> distributed = priceTube.getDistributedList();
            List<PriceTube.DistributedTube> distributedPercentList = priceTube.getDistributedPercentList(66.00d);
            distributedPercentList.sort((o1, o2) -> {
                if (o1.getPrice() > o2.getPrice()) {
                    return -1;
                } else if (o1.getPrice() < o2.getPrice()) {
                    return 1;
                }
                return 0;
            });
            double percentage = 0.00d;
            for (PriceTube.DistributedTube distributedTube : distributedPercentList) {
                if (distributedTube.getPrice() > 2160) {
                    percentage += distributedTube.getPercentage();
                }
            }
            System.out.println("end");
        }
        System.out.println("end");
    }

    @Test
    public void stockDayContainers() {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\d+)"));
        String stockCode = "sz300498";
        File directory = find(stockCode, list);
        if (null != directory) {
            List<File> files = defaultFileOperator.allFiles(directory, ((dir, name) -> name.matches("(\\d+)(\\.txt)")));
            for (File file : files) {
                Stock stock = new Stock(directory.getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(file.getName()));
                stockDayContainer.swallow(file);
                containers.add(stockDayContainer);
            }
        }
    }

    @Test
    public void oneStockDayPricesTrend() {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\d+)"));
        String stockCode = "sh601688";
        File directory = find(stockCode, list);
        long down = 0;
        long up = 0;
        long difference = Long.MAX_VALUE;
        LimitQueue<LocalDate> limitQueue = new LimitQueue(10);
        if (null != directory) {
            long yesterday = -1;
            List<File> files = defaultFileOperator.allFiles(directory, ((dir, name) -> name.matches("(\\d+)(\\.txt)")));
            for (File file : files) {
                Stock stock = new Stock(directory.getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(file.getName()));
                stockDayContainer.swallow(file);
                containers.add(stockDayContainer);
            }

            for (StockDayContainer container : containers) {
                long avgPrice = container.avgPrice();
                int hand = container.totalHand();
                if (yesterday == -1) {
                    up += hand;
                } else if (avgPrice > yesterday) {
                    up += hand;
                } else if (avgPrice < yesterday) {
                    down += hand;
                }

                yesterday = avgPrice;
                long ca = abs(up - down);
                if (ca < difference) {
                    difference = ca;
                    limitQueue.offer(container.getDateTime());
                }
            }
            System.out.println("down: " + down);
            System.out.println("up:" + up);
        }

        System.out.println("end: " + (double) up / (double) down);
    }


    public void oneStockDayPricesTrendContainers() {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\d+)"));

        for (File directory : list) {
            List<File> files = defaultFileOperator.allFiles(directory, ((dir, name) -> name.matches("(\\d+)(\\.txt)")));
            for (File file : files) {
                Stock stock = new Stock(directory.getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(file.getName()));
                stockDayContainer.swallow(file);
                containers.add(stockDayContainer);
            }

            for (StockDayContainer container : containers) {
                long down = 0;
                long up = 0;
                long yesterday = -1;
                long avgPrice = container.avgPrice();
                int hand = container.totalHand();
                if (yesterday == -1) {
                    up += hand;
                } else if (avgPrice > yesterday) {
                    up += hand;
                } else if (avgPrice < yesterday) {
                    down += hand;
                }
                yesterday = avgPrice;
            }


        }
    }
}
