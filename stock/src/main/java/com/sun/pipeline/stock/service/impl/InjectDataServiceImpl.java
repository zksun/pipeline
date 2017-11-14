package com.sun.pipeline.stock.service.impl;

import com.sun.pipeline.mybatis.dao.*;
import com.sun.pipeline.mybatis.domain.StockCodeDO;
import com.sun.pipeline.mybatis.domain.StockDayCountDO;
import com.sun.pipeline.stock.Contants;
import com.sun.pipeline.stock.StockFileOperator;
import com.sun.pipeline.stock.StockUtil;
import com.sun.pipeline.stock.command.BaseDayCommand;
import com.sun.pipeline.stock.command.StockDayCountCommand;
import com.sun.pipeline.stock.command.StockRightCommand;
import com.sun.pipeline.stock.domain.ExcludeRights;
import com.sun.pipeline.stock.domain.Stock;
import com.sun.pipeline.stock.explorer.sohu.SohuStockHttpCommandService;
import com.sun.pipeline.stock.price.StockDayContainer;
import com.sun.pipeline.stock.service.InjectDataService;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.sun.pipeline.stock.StockUtil.getRealTime;

/**
 * Created by zhikunsun on 2017/11/7.
 */
public class InjectDataServiceImpl implements InjectDataService {

    private SohuStockHttpCommandService sohuStockHttpCommandService = SohuStockHttpCommandService.getInstance();
    private StockFileOperator fileOperator;

    @Resource
    private StockBaseDAO stockBaseDAO;

    @Resource
    private StockCodeDAO stockCodeDAO;

    @Resource
    private StockRightDAO stockRightDAO;

    @Resource
    private StockDayCountDAO stockDayCountDAO;

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
    public boolean injectAllStockData(LocalDate start, LocalDate end) {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> files = StockUtil.find("empty", defaultFileOperator.allDirectory((dir, name)
                -> name.matches("(sz|sh)(\\d+)")), start, end, fileOperator);
        if (null != files && !files.isEmpty()) {
            for (File day : files) {
                Stock stock = new Stock(day.getParentFile().getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(day.getName()));
                stockDayContainer.swallow(day);
                try {
                    mainThreadPool.submit(new BaseDayCommand(stockDayContainer, stockBaseDAO, stockInjectLogDAO)).get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
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

    @Override
    public boolean injectAllStockExcludeRights() {
        List<File> files = fileOperator.allDirectory();
        if (!files.isEmpty()) {
            for (File file : files) {
                String stockCode = file.getName();
                List<ExcludeRights> excludeRightses = sohuStockHttpCommandService.getExcludeRightsInfo(Contants.DEFAULT_SOHU_INFO_HTTP_GET, stockCode);
                try {
                    mainThreadPool.submit(new StockRightCommand(excludeRightses, stockRightDAO)).get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean injectAllStockDayCount(LocalDate start, LocalDate end) {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> files = StockUtil.find("empty", defaultFileOperator.allDirectory((dir, name)
                -> name.matches("(sz|sh)(\\d+)")), start, end, fileOperator);
        if (null != files && !files.isEmpty()) {
            for (File day : files) {
                Stock stock = new Stock(day.getParentFile().getName());
                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(day.getName()));
                stockDayContainer.swallow(day);
                try {
                    mainThreadPool.submit(new StockDayCountCommand(stockDayContainer, stockDayCountDAO)).get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }

    @Override
    public boolean injectStockDayCount(String stockCode, LocalDate start, LocalDate end) {
        List<StockDayContainer> containers = new ArrayList<>();
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> files = StockUtil.find(stockCode, defaultFileOperator.allDirectory((dir, name)
                -> name.matches("(sz|sh)(\\d+)")), start, end, fileOperator);
        if (null != files && !files.isEmpty()) {
            for (File day : files) {
                Stock stock = new Stock(day.getParentFile().getName());

                long l = hasInjected(stockCode, convert(getRealTime(day.getName())));
                if (l > 0) {
                    continue;
                }

                StockDayContainer stockDayContainer = new StockDayContainer(stock, getRealTime(day.getName()));
                stockDayContainer.swallow(day);
                containers.add(stockDayContainer);
            }
        }
        if (!containers.isEmpty()) {
            for (StockDayContainer container : containers) {
                try {
                    mainThreadPool.submit(new StockDayCountCommand(container, stockDayCountDAO)).get();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        }
        return false;
    }

    private long hasInjected(String code, Date date) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("day", date);
        List<StockDayCountDO> query = stockDayCountDAO.query(map);
        if (null != query && !query.isEmpty()) {
            return query.get(0).getId();
        }
        return -1L;
    }

    private Date convert(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
