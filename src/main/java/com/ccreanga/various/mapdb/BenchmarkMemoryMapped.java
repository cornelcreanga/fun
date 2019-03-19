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

    static ConcurrentMap<String,Long> mapDbMemoryMapped;
    static ConcurrentMap<String,Long> mapDbNonMemoryMapped;
    static ConcurrentMap<String,Long> jdkMap =new ConcurrentHashMap<>();

    public static void fillMemoryMappedMap(){
        DB db = DBMaker
                .fileDB("/tmp/file.db")
                .fileMmapEnable()
                .make();
        mapDbMemoryMapped = db
                .hashMap("map", Serializer.STRING, Serializer.LONG)
                .createOrOpen();
        for (int i = 0; i < 100_000; i++) {
            mapDbMemoryMapped.put("item"+i, (long)i);
        }
        db.close();
    }

    public static void fillMemoryMap(){
        for (int i = 0; i < 100000; i++) {
            jdkMap.put("item"+i, (long)i);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void randomAccessMemory(){
        for (int i = 0; i < 1_000_000; i++) {
            jdkMap.get("item"+(long)(Math.random()*100000));
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void randomAccessMemoryMapped(){
        DB db = DBMaker
                .fileDB("/tmp/file.db")
                .fileMmapEnable()
                .make();
        mapDbMemoryMapped = db
                .hashMap("map", Serializer.STRING, Serializer.LONG)
                .createOrOpen();
        for (int i = 0; i < 1_000_000; i++) {
            mapDbMemoryMapped.get("item"+(long)(Math.random()*100000));
        }
        db.close();
    }

//    @Benchmark
//    @BenchmarkMode(Mode.AverageTime) @OutputTimeUnit(TimeUnit.MILLISECONDS)
//    @Fork(value = 1)
//    @Warmup(iterations = 2)
//    @Measurement(iterations = 5)
//    public static void randomAccessNonMemoryMapped(){
//        DB db = DBMaker
//                .fileDB("/tmp/file.db")
//                .make();
//        mapDbMemoryMapped = db
//                .hashMap("map", Serializer.STRING, Serializer.LONG)
//                .createOrOpen();
//        for (int i = 0; i < 100_000; i++) {
//            mapDbMemoryMapped.get("item"+(long)(Math.random()*100000));
//        }
//        db.close();
//    }

    /**
     * osx
     Benchmark                                       Mode  Cnt     Score    Error  Units
     BenchmarkMemoryMapped.randomAccessMemory        avgt    5    69.033 ±  5.212  ms/op
     BenchmarkMemoryMapped.randomAccessMemoryMapped  avgt    5  1800.507 ± 30.767  ms/op
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
