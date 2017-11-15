package com.sun.pipeline.file;

import com.sun.pipeline.stock.StockFileOperator;
import com.sun.pipeline.stock.StockUtil;
import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by zhikunsun on 2017/11/15.
 */
public class FileTest {
    public StockFileOperator fileOperator = new StockFileOperator(SystemConfig.getInstance().getProp("com.sun.stock.date.path", ""));

    @Test
    public void getFilesTest() {
        LocalDate start = LocalDate.parse("20170327", DateTimeFormatter.ofPattern("yyyyMMdd"));
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> files = StockUtil.find("empty", defaultFileOperator.allDirectory((dir, name)
                -> name.matches("(sz|sh)(\\d+)")), start, start, fileOperator);
        System.out.printf(new Integer(files.size()).toString());
    }
}