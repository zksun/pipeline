package com.sun.pipeline.util.internal.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * Created by zhikunsun on 17/9/24.
 */
public interface FileOperator {
    List<File> allDirectory();

    List<File> allDirectory(FilenameFilter filter);

    List<File> allFiles(File directory);

    List<File> allFiles(File directory, FilenameFilter filter);
}
