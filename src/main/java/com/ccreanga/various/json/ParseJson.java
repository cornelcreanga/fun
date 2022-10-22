package com.ccreanga.various.json;

import com.ccreanga.various.mapdb.*;
import com.google.common.io.ByteStreams;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

import static com.ccreanga.various.mapdb.Common.MAP_SIZE;
import static com.ccreanga.various.mapdb.Common.rand;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class ParseJson {

    private static ObjectMapper map = new ObjectMapper();
    static byte[] jsonBytes;
    static String jsonString;


    @Setup(Level.Trial)
    public static void fillMemoryMap() throws IOException {
        map = new ObjectMapper();
        jsonBytes =  ByteStreams.toByteArray(ParseJson.class.getResourceAsStream("/test.json"));
        jsonString = new String(jsonBytes,"UTF-8");
    }

    @Benchmark
    public static void parseJson() throws IOException {
        for (int i = 0; i < 1_000_000; i++) {
            map.readTree(jsonBytes);
//            map.readTree(new String(jsonBytes,"UTF-8"));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt;

        opt = new OptionsBuilder()
                .include(ParseJson.class.getSimpleName())
                .verbosity(VerboseMode.NORMAL)
                .build();
        new Runner(opt).run();

    }
}
