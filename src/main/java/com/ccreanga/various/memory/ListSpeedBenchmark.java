package com.ccreanga.various.memory;

import static com.ccreanga.various.mapdb.Common.*;

import com.ccreanga.various.mapdb.JdkMapBenchmark;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.sux4j.util.EliasFanoLongBigList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
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
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class ListSpeedBenchmark {
    static int SIZE = 1000000;
    static int ITERATIONS = 1000000;
    public static int[] intRand;
    private static  List<Long> jdkList;
    private static LongArrayList fastUtilList;
    private static  EliasFanoLongBigList succintList;


    @Setup(Level.Trial)
    public static void fillMemoryMap() {
        jdkList = new ArrayList<>(SIZE);
        fastUtilList = new LongArrayList(SIZE);
        intRand = new int[ITERATIONS];
        for (int i = 0; i < SIZE; i++) {
            jdkList.add((long)(Math.random()*Integer.MAX_VALUE));
            fastUtilList.add((long)(Math.random()*Integer.MAX_VALUE));
        }
        succintList = new EliasFanoLongBigList(LongIterators.asLongIterator(jdkList.iterator()));

        for (int i = 0; i < ITERATIONS; i++) {
            intRand[i]=(int)(Math.random()*i);
        }
    }

    @Benchmark
    public static void randomAccessJdk() {
        for (int i = 0; i < ITERATIONS; i++) {
            jdkList.get(intRand[i]);
        }
    }
    @Benchmark
    public static void randomAccessFastUtil() {
        for (int i = 0; i < ITERATIONS; i++) {
            fastUtilList.getLong(intRand[i]);
        }
    }
    @Benchmark
    public static void randomAccessSuccint() {
        for (int i = 0; i < ITERATIONS; i++) {
            succintList.getLong(intRand[i]);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt;

        opt = new OptionsBuilder()
            .include(ListSpeedBenchmark.class.getSimpleName())
            .verbosity(VerboseMode.NORMAL)
            .build();
        new Runner(opt).run();
    }


}
