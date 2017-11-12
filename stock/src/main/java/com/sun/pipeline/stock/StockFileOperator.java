package com.sun.pipeline.stock;

import com.sun.pipeline.util.internal.io.file.DefaultFileOperator;
import com.sun.pipeline.util.internal.io.file.FileOperator;

import java.io.File;
import java.util.List;

/**
 * Created by zhikunsun on 2017/11/12.
 */
public class StockFileOperator {
    private FileOperator fileOperator;

    public StockFileOperator(String stockDataPath) {
        this.fileOperator = new DefaultFileOperator(stockDataPath);
    }


    public List<File> allDirectory() {
        return fileOperator.allDirectory((dir, name) -> name.matches("(sz|sh)(\\w+)"));
    }

    public List<File> allTradeFile(File directory) {
        return fileOperator.allFiles(directory, ((dir, name) -> name.matches("(\\d+)(\\.txt)")));
    }


}
