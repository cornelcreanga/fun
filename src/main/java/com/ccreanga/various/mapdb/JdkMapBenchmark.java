package com.ccreanga.various.mapdb;

import static com.ccreanga.various.mapdb.Common.ITERATIONS;
import static com.ccreanga.various.mapdb.Common.MAP_SIZE;
import static com.ccreanga.various.mapdb.Common.random;

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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class JdkMapBenchmark {

    private static ConcurrentMap<Long, String> map = new ConcurrentHashMap<>();

    @Setup(Level.Trial)
    public static void fillMemoryMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            map.put((long) i, "item" + i);
        }
    }

    @Benchmark
    @Threads(1)
    public static void randomAccess() {
        for (int i = 0; i < ITERATIONS; i++) {
            map.get(random(MAP_SIZE));
        }

    }

    @Benchmark
    public static void randomUpdate() {
        for (int i = 0; i < Common.ITERATIONS; i++) {
            map.put((long) i, "item2" + i);
        }
    }

}
