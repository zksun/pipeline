package com.sun.pipeline;


/**
 * Created by zksun on 22/05/2017.
 */
public class MemoryTest {


    private final static class FalseSharing implements Runnable {

        public final static int NUM_THREADS = 4;
        public final static long ITERATIONS = 500L * 1000L * 1000L;
        private final int arrayIndex;

        private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];


        static {
            for (int i = 0; i < longs.length; i++) {
                longs[i] = new VolatileLong();
            }
        }


        private FalseSharing(int arrayIndex) {
            this.arrayIndex = arrayIndex;
        }

        static void runTest() throws InterruptedException {
            Thread[] threads = new Thread[NUM_THREADS];
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(new FalseSharing(i));
            }

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                t.join();
            }

        }


        public void run() {
            long i = ITERATIONS + 1;
            while (0 != --i) {
                longs[arrayIndex].value = i;
            }

        }
    }

    private final static class VolatileLong {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6;
    }

    public static void main(String[] args) {
        final long start = System.nanoTime();
        try {
            FalseSharing.runTest();
            System.out.println("duration = " + (System.nanoTime() - start));
        } catch (InterruptedException e) {
            //ignore
        }

    }

}
