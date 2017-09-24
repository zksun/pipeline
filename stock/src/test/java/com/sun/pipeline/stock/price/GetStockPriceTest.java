package com.sun.pipeline.stock.price;

import com.sun.pipeline.stock.system.SystemConfig;
import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import org.junit.Test;

import java.io.File;
import java.util.List;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class GetStockPriceTest {
    @Test
    public void getDayPriceTest() {

    }

    @Test
    public void defaultFileOperator() {
        String configFilePath = SystemConfig.getInstance().getProp(SystemConfig.DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME, "");
        DefaultFileOperator defaultFileOperator = new DefaultFileOperator(configFilePath);
        List<File> list = defaultFileOperator.allDirectory();
        for(File file : list){
            System.out.println(file);
        }
    }
}
