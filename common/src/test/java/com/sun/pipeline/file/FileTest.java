package com.sun.pipeline.file;

import com.sun.pipeline.util.internal.logging.InternalLogger;
import com.sun.pipeline.util.internal.logging.InternalLoggerFactory;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;

/**
 * Created by zksun on 16/05/2017.
 */
public class FileTest {

    private static final InternalLogger logger = InternalLoggerFactory.getInstance("sun.pipeline.log.test");

    @Test
    public void getStockValues() {
        Iterator<File> fileIterator = FileUtils.iterateFiles(new File("/Users/hanshou/Downloads/sh600352"), new IOFileFilter() {
            public boolean accept(File file) {
                //logger.info(file.getName());
                return true;
            }

            public boolean accept(File file, String s) {
                return false;
            }
        }, null);

        while (fileIterator.hasNext()) {
            File next = fileIterator.next();
            try {

                FileInputStream fileInputStream = FileUtils.openInputStream(next);
                byte[] buf = new byte[16];
                while (-1 != fileInputStream.read(buf)) {
                    ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
                    byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    int sequence = getRealSequence(byteBuffer.getShort());
                    int price = byteBuffer.getInt();
                    int tradeLot = byteBuffer.getInt();
                    int traceNum = byteBuffer.getInt();
                    int bs = byteBuffer.getShort();
                    logger.info("the sequence is: {}, and the price is: {}, and the tradeLot is: {}, " +
                            " the tradeNum is: {}, and the bs is: {} ", sequence, price, tradeLot, traceNum, bs);
                }
            } catch (IOException e) {
                //ignore
            }
        }
    }

    private static int getRealSequence(int source) {
        if (source < 780) {
            return source - 570;
        } else {
            return source - 660;
        }
    }
}
