package com.ccreanga.various.mapdb;

import static com.ccreanga.various.mapdb.Common.ITERATIONS;
import static com.ccreanga.various.mapdb.Common.MAP_SIZE;
import static com.ccreanga.various.mapdb.Common.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.mapdb.Serializer;
import org.mapdb.SortedTableMap;
import org.mapdb.volume.MappedFileVol;
import org.mapdb.volume.Volume;
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
public class MapDbSTableBenchmark {

    public static final String DB_PATH = "/tmp/s-table.db";
    private static ConcurrentMap<Long, String> map;

    @Setup(Level.Trial)
    public static void fillMemoryMappedMap() throws IOException {

        Path dbPath = newPath(DB_PATH);
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
    @Threads(4)
    public static void randomAccess() {
        for (int i = 0; i < ITERATIONS; i++) {
            map.get(rand[i]);
        }
    }

}
