package com.ccreanga.various.mapdb;

import org.mapdb.Serializer;
import org.mapdb.SortedTableMap;
import org.mapdb.volume.MappedFileVol;
import org.mapdb.volume.Volume;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import static com.ccreanga.various.mapdb.Common.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
@Warmup(iterations = 2)
@Measurement(iterations = 5)
public class MapDbSTableBenchmark {

    private static ConcurrentMap<Long, String> map;

    @Setup(Level.Trial)
    public static void fillMemoryMappedMap() throws IOException {

        Path dbPath = Paths.get("/tmp/s-table.db");

        if (dbPath.toFile().exists())
            Files.delete(dbPath);
        Volume volume = MappedFileVol.FACTORY.makeVolume(dbPath.toString(), false);

        SortedTableMap.Sink<Long, String> sink =
                SortedTableMap.create(
                        volume,
                        Serializer.LONG,
                        Serializer.STRING
                ).createFromSink();

        for (int key = 0; key < MAP_SIZE; key++) {
            sink.put((long) key, "value" + key);
        }
        map = sink.create();
    }

    @Benchmark
    public static void randomAccess() {
        for (int i = 0; i < ITERATIONS; i++) {
            map.get(random(MAP_SIZE));
        }
    }

}
