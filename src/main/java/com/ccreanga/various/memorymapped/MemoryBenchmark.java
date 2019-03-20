package com.ccreanga.various.memorymapped;

import com.ccreanga.various.tree.TreeBenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryBenchmark {

    private static int count = 10*1024*1024;
    private static byte[] memoryArray = null;




    private static void createByteArray() {
        memoryArray = new byte[count];
        Random random = new SecureRandom();
        random.nextBytes(memoryArray);
    }

    static{
        createByteArray();
    }



    @org.openjdk.jmh.annotations.Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void testReadMemory(){
        long counter = 0;
        for (byte b : memoryArray) {
            counter += b;
        }
    }

    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(MemoryBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}
