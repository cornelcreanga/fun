package com.ccreanga.various.memorymapped;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

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
