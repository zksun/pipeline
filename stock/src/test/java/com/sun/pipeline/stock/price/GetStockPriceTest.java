package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public void allStockDayContainer() {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\d+)"));
        int i = 0;
        for (File directory : list) {
            System.out.println("now add " + i + " stock containers with stock id: " + directory.getName());
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
            i++;
            System.out.println("add " + i + " stock containers with stock id: " + directory.getName());
        }

        System.out.println(containers);
    }
}
