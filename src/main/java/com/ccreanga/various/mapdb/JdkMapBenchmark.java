package com.ccreanga.various.mapdb;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.ccreanga.various.mapdb.Common.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
@Warmup(iterations = 2)
@Measurement(iterations = 5)
public class JdkMapBenchmark {

    private static ConcurrentMap<Long, String> jdkMap = new ConcurrentHashMap<>();

    @Setup(Level.Trial)
    public static void fillMemoryMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            jdkMap.put((long) i, "item" + i);
        }
    }

    @Benchmark
    public static void randomAccess() {
        for (int i = 0; i < ITERATIONS; i++) {
            jdkMap.get(random(MAP_SIZE));
        }
    }
}
