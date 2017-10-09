package com.sun.pipeline.stock.system;

import com.sun.pipeline.util.internal.Platform;
import com.sun.pipeline.util.props.Props;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhikunsun on 17/9/24.
 */
public class SystemConfig implements Props {

    public final static String DEFAULT_SYSTEM_PROPERTIES_CONFIG_NAME = "com.sun.stock.date.path";

    private final static SystemConfig instance = new SystemConfig();

    private final Properties properties;

    private SystemConfig() {
        properties = new Properties();
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("system.properties");
            properties.load(new BufferedInputStream(resourceAsStream));
        } catch (Exception e) {
            Platform.throwException(e);
        }
    }

    @Override
    public <T> T getProp(String key, T defaultValue) {
        T value = (T) properties.get(key);
        return (null == value || value.equals("")) ? defaultValue : value;
    }

    public static SystemConfig getInstance() {
        return instance;
    }

    public static ExecutorService systemExecutorService() {
        return Executors.newFixedThreadPool(6);
    }

    public void execute(Runnable runnable) {
        systemExecutorService().execute(runnable);
    }

    <T> List<Future<T>> executeAll(Collection<? extends Callable<T>> tasks) {
        try {
            return systemExecutorService().invokeAll(tasks);
        } catch (Exception e) {
            Platform.throwException(e);
        }
        return Collections.emptyList();
    }


}
