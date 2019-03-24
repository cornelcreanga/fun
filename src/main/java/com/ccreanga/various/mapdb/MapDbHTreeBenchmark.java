package com.ccreanga.various.mapdb;

import static com.ccreanga.various.mapdb.Common.newPath;
import static com.ccreanga.various.mapdb.Common.random;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class MapDbHTreeBenchmark {

    public static final String DB_PATH = "/tmp/h-tree.db";
    private static ConcurrentMap<Long, String> map;
    private static DB db;

    @Setup(Level.Trial)
    public static void fillMemoryMappedMap() throws IOException {

        Path dbPath = newPath(DB_PATH);

        db = DBMaker
            .fileDB(dbPath.toString())
            .fileMmapEnable()
            .make();
        map = db
            .hashMap("map", Serializer.LONG, Serializer.STRING)
            .create();
        for (int i = 0; i < Common.MAP_SIZE; i++) {
            map.put((long) i, "item" + i);
        }
        db.commit();
    }

    @Benchmark
    public static void randomAccess() {
        for (int i = 0; i < Common.ITERATIONS; i++) {
            map.get(random(Common.MAP_SIZE));
        }
    }

    @Benchmark
    public static void randomUpdate() {
        for (int i = 0; i < Common.ITERATIONS; i++) {
            map.put((long) i, "item2" + i);
        }
        db.commit();
    }

    @Benchmark
    @Warmup(iterations = 0)
    @Measurement(iterations = 1)
    public static void xCompact() {
        db.getStore().compact();
        db.commit();
    }

}
