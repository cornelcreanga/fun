package com.ccreanga.various.mapdb;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class BenchmarkMemoryMapped {

    public static final int MAP_SIZE = 100_000;
    public static final int ITERATIONS = 1_000_000;
    static ConcurrentMap<String, Long> mapDbMemoryMapped;
    static ConcurrentMap<String, Long> mapDbNonMemoryMapped;
    static ConcurrentMap<String, Long> jdkMap = new ConcurrentHashMap<>();

    public static void fillMemoryMappedMap() {
        DB db = DBMaker
                .fileDB("/tmp/file.db")
                .fileMmapEnable()
                .make();
        mapDbMemoryMapped = db
                .hashMap("map", Serializer.STRING, Serializer.LONG)
                .createOrOpen();
        for (int i = 0; i < MAP_SIZE; i++) {
            mapDbMemoryMapped.put("item" + i, (long) i);
        }
        db.close();
    }

    public static void fillMemoryMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            jdkMap.put("item" + i, (long) i);
        }
    }

    //    @MemoryBenchmark
//    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 5)
    public static void randomAccessMemory() {
        for (int i = 0; i < ITERATIONS; i++) {
            jdkMap.get("item" + (long) (Math.random() * MAP_SIZE));
        }
    }

    //    @MemoryBenchmark
//    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 5)
    public static void randomAccessMemoryMapped() {
        DB db = DBMaker
                .fileDB("/tmp/file.db")
                .fileMmapEnable()
                .make();
        mapDbMemoryMapped = db
                .hashMap("map", Serializer.STRING, Serializer.LONG)
                .createOrOpen();
        for (int i = 0; i < ITERATIONS; i++) {
            mapDbMemoryMapped.get("item" + (long) (Math.random() * MAP_SIZE));
        }
        db.close();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void randomAccessNonMemoryMapped() {
        DB db = DBMaker
                .fileDB("/tmp/file.db")
                .make();
        mapDbNonMemoryMapped = db
                .hashMap("map", Serializer.STRING, Serializer.LONG)
                .createOrOpen();
        for (int i = 0; i < ITERATIONS; i++) {
            mapDbNonMemoryMapped.get("item" + (long) (Math.random() * MAP_SIZE));
        }
        db.close();
    }

    /**
     * osx/job
     * MemoryBenchmark                                       Mode  Cnt     Score    Error  Units
     * BenchmarkMemoryMapped.randomAccessMemory        avgt    5    69.033 ±  5.212  ms/op
     * BenchmarkMemoryMapped.randomAccessMemoryMapped  avgt    5  1800.507 ± 30.767  ms/op
     * <p>
     * linux/home
     * MemoryBenchmark                                       Mode  Cnt     Score    Error  Units
     * BenchmarkMemoryMapped.randomAccessMemory        avgt    5    60.496 ±  0.439  ms/op
     * BenchmarkMemoryMapped.randomAccessMemoryMapped  avgt    5  1257.855 ± 32.354  ms/op
     * BenchmarkMemoryMapped.randomAccessNonMemoryMapped  avgt    5  30739.443 ± 408.829  ms/op
     */

    public static void main(String[] args) throws RunnerException {

        fillMemoryMappedMap();
        fillMemoryMap();
        Options opt = new OptionsBuilder()
                .include(BenchmarkMemoryMapped.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}
