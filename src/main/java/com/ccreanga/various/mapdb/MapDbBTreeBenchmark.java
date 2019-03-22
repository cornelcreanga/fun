package com.ccreanga.various.mapdb;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.ccreanga.various.mapdb.Common.random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
@Warmup(iterations = 2)
@Measurement(iterations = 5)
public class MapDbBTreeBenchmark {

    private static ConcurrentMap<Long, String> map;

    @Setup(Level.Trial)
    public static void fillMemoryMappedMap() throws IOException {

        Path dbPath = Paths.get("/tmp/b-tree.db");

        if (dbPath.toFile().exists())
            Files.delete(dbPath);

        DB db = DBMaker
                .fileDB(dbPath.toString())
                .fileMmapEnable()
                .make();
        map = db
                .treeMap("map", Serializer.LONG, Serializer.STRING)
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

}
