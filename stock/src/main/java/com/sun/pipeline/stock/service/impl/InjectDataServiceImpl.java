package com.sun.pipeline.stock.service.impl;

import com.sun.pipeline.mybatis.dao.StockBaseDAO;
import com.sun.pipeline.mybatis.dao.StockCodeDAO;
import com.sun.pipeline.mybatis.domain.StockCodeDO;
import com.sun.pipeline.stock.StockFileOperator;
import com.sun.pipeline.stock.StockUtil;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.price.StockDayContainer;
import com.sun.pipeline.stock.service.InjectDataService;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public InjectDataServiceImpl() {
        this.fileOperator = new StockFileOperator(SystemConfig.getInstance().getProp("com.sun.stock.date.path", ""));
    }

    @Override
    public boolean injectStockData(String stockCode, LocalDate start, LocalDate end) {
        return false;
    }

    @Override
    public boolean injectAllStockCode() {
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
            }
            stockCodeDAO.batchInsert(stockCodeDOs);
            return true;
        }
        return false;
    }


    private List<StockDayContainer> getAllDayContainers() {
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
        return containers;
    }


}
