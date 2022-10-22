package com.ccreanga.various.bits;

import com.ccreanga.various.mapdb.JdkMapBenchmark;
import com.ccreanga.various.mapdb.MapDbBTreeBenchmark;
import com.ccreanga.various.mapdb.MapDbHTreeBenchmark;
import com.ccreanga.various.mapdb.MapDbSTableBenchmark;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ccreanga.various.mapdb.Common.ITERATIONS;
import static com.ccreanga.various.mapdb.Common.rand;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class BitSetBenchmark {

    private static Map<String, String> map;
    private static BitSet[] bitIndex;
    private static boolean[][] booleanIndex;
    public static String[] values = new String[]{
            "colour",
            "item",
            "event",
            "age",
            "various",
            "price",
            "address",
            "street",
            "city",
            "floor",
    };
    public static final int ITERATIONS = 1_000_000;


    @Setup(Level.Trial)
    public static void fillMemoryMappedMap() throws IOException {
        bitIndex = new BitSet[2];
        booleanIndex = new boolean[2][256];
        map = new HashMap<>();
        for (int i = 0; i < bitIndex.length; i++) {
            bitIndex[i] = new BitSet(256);
        }
//        for (int i = 0; i < booleanIndex.length; i++) {
//            booleanIndex[i] = new boolean[256];
//        }
        for (String value : values) {
            map.put(value,value);
            if (value.length() > 0) {
                bitIndex[0].set(value.charAt(0));
                booleanIndex[0][value.charAt(0)] = true;
            }
            if (value.length() > 1) {
                bitIndex[1].set(value.charAt(1));
                booleanIndex[1][value.charAt(1)] = true;
            }
        }

    }

    @Benchmark
    public static void mapAccess(Blackhole b) {
        for (int i = 0; i < ITERATIONS; i++) {
            String key = values[i % values.length];
            //b.consume(map.containsKey(key));
            map.containsKey(key);
        }
    }
    @Benchmark
    public static void bitsetAccess(Blackhole b) {
        for (int i = 0; i < ITERATIONS; i++) {
            String key = values[i % values.length];
            if (key.length() > 0) {
                if  (!(bitIndex[0].get(key.charAt(0))))
                    continue;
            }
            if (key.length() > 1) {
                if  (!(bitIndex[1].get(key.charAt(1))))
                    continue;
            }
        }
    }
    @Benchmark
    public static void booleanAccess(Blackhole b) {
        for (int i = 0; i < ITERATIONS; i++) {
            //String key = values[i % values.length];
            String key = "abc";
            if (key.length() > 0) {
                if  (!(booleanIndex[0][key.charAt(0)]))
                    continue;
            }
            if (key.length() > 1) {
                if  (!(booleanIndex[0][key.charAt(1)]))
                    continue;
            }
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt;
        BitSet b = new BitSet(64);
        opt = new OptionsBuilder()
                .include(BitSetBenchmark.class.getSimpleName())
                .verbosity(VerboseMode.NORMAL)
                .build();
        new Runner(opt).run();

    }

}
