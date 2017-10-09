package com.sun.pipeline.util.internal.io.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class DefaultFileOperator implements FileOperator {

    private final String path;

    public DefaultFileOperator(String path) {
        this.path = path;
    }

    @Override
    public List<File> allDirectory() {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new UnsupportedOperationException("no directory");
        }
        List<File> list = Collections.EMPTY_LIST;
        File[] files = directory.listFiles();
        if (files.length > 0) {
            list = new LinkedList<>();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    @Override
    public List<File> allDirectory(FilenameFilter filter) {
        File directory = new File(path);
        if (!directory.isDirectory()) {
            throw new UnsupportedOperationException("no directory");
        }
        List<File> list = Collections.EMPTY_LIST;
        File[] files = directory.listFiles(filter);
        if (files.length > 0) {
            list = new LinkedList<>();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    @Override
    public List<File> allFiles(File directory) {
        if (null == directory) {
            throw new NullPointerException("directory");
        }
        if (!directory.isDirectory()) {
            throw new NullPointerException("not directory");
        }
        List<File> list = Collections.EMPTY_LIST;
        File[] files = directory.listFiles();
        if (files.length > 0) {
            list = new LinkedList<>();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    list.add(file);
                }
            }
        }
        return list;
    }

    @Override
    public List<File> allFiles(File directory, FilenameFilter filter) {
        if (null == directory) {
            throw new NullPointerException("directory");
        }
        if (!directory.isDirectory()) {
            throw new NullPointerException("not directory");
        }
        List<File> list = Collections.EMPTY_LIST;
        File[] files = directory.listFiles(filter);
        if (files.length > 0) {
            list = new LinkedList<>();
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    list.add(file);
                }
            }
        }
        return list;
    }
}
