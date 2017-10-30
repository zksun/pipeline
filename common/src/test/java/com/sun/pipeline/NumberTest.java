package com.sun.pipeline;

import org.junit.Test;

/**
 * Created by zhikunsun on 2017/10/27.
 */
public class NumberTest {

    @Test
    public void getKeyTest() {
        String source = "application_1508482501578_1135";
        source = getProtocolString(source);
        System.out.println(ELFHash(source) % 30);
    }

    private static long ELFHash(String str) {
        long hash = 0;
        long x;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = (hash & 0xF000000000000000L)) != 0) {
                hash ^= (x >>> 56);
                hash &= ~x;
            }
            System.out.println("hash i: " + i + ": " + hash);
        }

        return (hash & 0x7FFFFFFFFFFFFFFFL);
    }

    private static String getProtocolString(String str) {
        char[] result = new char[17 + str.length()];
        for (int i = 0; i < 16; i++) {
            result[i] = 0x0;
        }
        result[16] = 0x03;
        for (int i = 17, j = 0; j < str.length(); i++, j++) {
            result[i] = str.charAt(j);
        }
        return new String(result);
    }
}
