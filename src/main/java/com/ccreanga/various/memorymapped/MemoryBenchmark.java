package com.ccreanga.various.memorymapped;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
@Warmup(iterations = 2)
@Measurement(iterations = 5)
public class MemoryBenchmark {

    static long counter = 0;
    private static int count = 50 * 1024 * 1024;
    private static byte[] memoryArray = null;

    @Setup(Level.Trial)
    public static void createByteArray() {
        memoryArray = new byte[count];
        Random random = new SecureRandom();
        random.nextBytes(memoryArray);
    }

    @Benchmark
    public static void testReadMemory() {
        for (byte b : memoryArray) {
            counter += b;
        }
    }

    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(MemoryBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
        System.out.println(counter);
    }

}
