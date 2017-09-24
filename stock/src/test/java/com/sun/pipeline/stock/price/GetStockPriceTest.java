package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.Trade;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

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
    public void StockDayContiner() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\w+)"));
        List<File> files = defaultFileOperator.allFiles(list.get(0), ((dir, name) -> name.matches("(\\w+)(\\.txt)")));
        Stock stock = new Stock(list.get(0).getName());
        StockDayContainer stockDayContainer = new StockDayContainer(stock, LocalDate.of(2017, 9, 21));
        files.forEach(file -> stockDayContainer.swallow(file));
        System.out.println(stockDayContainer);
    }
}
