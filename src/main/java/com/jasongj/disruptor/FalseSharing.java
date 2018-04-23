package com.jasongj.disruptor;

public class FalseSharing {

    private volatile long first, second;

    private long a1, a2, a3, a4, a5, a6, a7;
    private volatile long third;
    private long b1, b2, b3, b4, b5, b6, b7;
    private volatile long forth;
    private long c1, c2, c3, c4, c5, c6, c7;

    public static void main(String[] args) throws InterruptedException {

        FalseSharing falseSharing = new FalseSharing();
        long max = Integer.MAX_VALUE;

//        Runnable updateFirst = () -> {for (; falseSharing.first++ <= max;){}};
//        Runnable updateSecond = () -> {for (; falseSharing.second++ <= max;){}};
//        Runnable updateThird = () -> {for (; falseSharing.third++ <= max;){}};
//        Runnable updateForth = () -> {for (; falseSharing.forth++ <= max;){}};
//        test("False sharing without padding: %s \n", updateFirst, updateSecond);
//        test("Padded cache line: %s \n", updateThird, updateForth);

        test("False sharing without padding: %s \n", () -> {for (; falseSharing.first++ <= max;){}}, () -> {for (; falseSharing.second++ <= max;){}});
        test("Padded cache line: %s \n", () -> {for (; falseSharing.third++ <= max;){}}, () -> {for (; falseSharing.forth++ <= max;){}});
    }

    public static void test (String desc, Runnable... useCases) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[useCases.length];
        for(int i = 0; i < useCases.length; i++) {
            threads[i] = new Thread(useCases[i]);
            threads[i].start();
        }
        for(Thread thread : threads) {
            thread.join();
        }
        long end = System.currentTimeMillis();
        System.out.printf(desc, end - start);
    }

}
