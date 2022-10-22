package com.ccreanga.various.mapdb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Common {

    public static final int ITERATIONS = 1_000_000;
    public static final int MAP_SIZE = 5_000_000;
    public static long[] rand = new long[ITERATIONS];

    static {
        for (int i = 0; i < ITERATIONS; i++) {
            rand[i]=random(MAP_SIZE);
        }
    }

    public static long random(int max) {
        return (long) (Math.random() * max);
    }

    public static long size(String path) throws IOException {
        return Files.size(Paths.get(path));
    }

    public static Path newPath(String path) throws IOException {
        Path dbPath = Paths.get(path);
        if (dbPath.toFile().exists()) {
            Files.delete(dbPath);
        }
        return dbPath;
    }

}
