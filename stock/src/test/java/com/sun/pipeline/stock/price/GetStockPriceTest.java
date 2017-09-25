package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.calculate.PriceComparators;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.domain.StockPrice;
import com.sun.pipeline.stock.domain.Trade;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

import static com.sun.pipeline.stock.StockUtil.compareSellBuyList;
import static com.sun.pipeline.stock.StockUtil.find;
import static com.sun.pipeline.stock.StockUtil.getRealTime;

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
            for (StockDayContainer container : containers) {
                List<StockPrice> data = container.getData();
                for (StockPrice price : data) {
                    if (price.getTrade().equals(Trade.BUY)) {
                        buyList.add(price);
                    } else if (price.getTrade().equals(Trade.SELL)) {
                        sellList.add(price);
                    }
                }
            }
            buyList.sort(new PriceComparators.PriceAscComparator());
            sellList.sort(new PriceComparators.PriceDesComparator());

            List<StockPrice> difference = compareSellBuyList(sellList, buyList);
            PriceTube priceTube = new PriceTube();
            for (StockPrice price : difference) {
                priceTube.add(price);
            }
            List<PriceTube.DistributedTube> distributed = priceTube.getDistributed();
            System.out.println("end");
        }

    }


}
