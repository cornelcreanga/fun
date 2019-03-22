package com.ccreanga.various.mapdb;

public class Common {
    public static final int ITERATIONS = 1_000_000;
    public static final int MAP_SIZE = 5_000_000;

    public static long random(int max) {
        return (long) (Math.random() * max);
    }
}
