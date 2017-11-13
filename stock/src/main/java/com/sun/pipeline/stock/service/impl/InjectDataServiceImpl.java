package com.sun.pipeline.stock.service.impl;

import com.sun.pipeline.mybatis.dao.StockBaseDAO;
import com.sun.pipeline.mybatis.dao.StockCodeDAO;
import com.sun.pipeline.mybatis.dao.StockInjectLogDAO;
import com.sun.pipeline.mybatis.domain.StockCodeDO;
import com.sun.pipeline.stock.StockFileOperator;
import com.sun.pipeline.stock.StockUtil;
import com.sun.pipeline.stock.command.BaseDayCommand;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.price.StockDayContainer;
import com.sun.pipeline.stock.service.InjectDataService;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.pipeline.stock.StockUtil.getRealTime;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class InjectDataServiceImpl implements InjectDataService {

    private StockFileOperator fileOperator;

    @Resource
    private StockBaseDAO stockBaseDAO;

    @Resource
    private StockCodeDAO stockCodeDAO;

    @Resource
    private StockInjectLogDAO stockInjectLogDAO;

    @Resource
    private ThreadPoolTaskExecutor mainThreadPool;

    public InjectDataServiceImpl() {
        this.fileOperator = new StockFileOperator(SystemConfig.getInstance().getProp("com.sun.stock.date.path", ""));
    }

    @Override
    public boolean injectStockData(String stockCode, LocalDate start, LocalDate end) {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> files = StockUtil.find(stockCode, defaultFileOperator.allDirectory((dir, name)
                -> name.matches("(sz|sh)(\\d+)")), start, end, fileOperator);
        if (null != files && !files.isEmpty()) {
            for (File day : files) {
                Stock stock = new Stock(day.getParentFile().getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(day.getName()));
                stockDayContainer.swallow(day);
                containers.add(stockDayContainer);
            }
        }
        if (!containers.isEmpty()) {
            for (StockDayContainer container : containers) {
                try {
                    mainThreadPool.submit(new BaseDayCommand(container, stockBaseDAO, stockInjectLogDAO)).get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
            return true;
        }

        return false;
    }

    @Override
    public boolean injectAllStockCode() {
        stockCodeDAO.clear();
        List<File> files = fileOperator.allDirectory();
        if (!files.isEmpty()) {
            List<StockCodeDO> stockCodeDOs = new ArrayList<>();
            for (File file : files) {
                String fullName = file.getName();
                String code = StockUtil.getRealStockCode(fullName);
                String market = StockUtil.getMarket(fullName);
                StockCodeDO stockCodeDO = new StockCodeDO();
                stockCodeDO.setCode(Integer.valueOf(code));
                stockCodeDO.setMarket(market);
                stockCodeDO.setFullName(fullName);
                stockCodeDOs.add(stockCodeDO);
            }
            stockCodeDAO.batchInsert(stockCodeDOs);
            return true;
        }
        return false;
    }


    private void injectAllDayContainers() {
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
                    mainThreadPool.execute(new BaseDayCommand(stockDayContainer, stockBaseDAO, stockInjectLogDAO));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
