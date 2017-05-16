package com.sun.pipeline.logging;


import com.sun.pipeline.util.internal.logging.InternalLogger;
import com.sun.pipeline.util.internal.logging.InternalLoggerFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zksun on 15/05/2017.
 */
public class LoggingTest {
    private final static Logger logger = LoggerFactory.getLogger("sun.pipeline.log.test");

    private final static InternalLogger internalLogger = InternalLoggerFactory.getInstance("sun.pipeline.log.test");

    @Test
    public void printLoggingTest() {
        logger.info("print pipeline first log!");
    }

    @Test
    public void printInternalLoggingTest() {
        internalLogger.info("print pipeline fire internal log");
    }
}
