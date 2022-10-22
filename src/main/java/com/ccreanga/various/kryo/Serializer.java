package com.ccreanga.various.kryo;

import static com.ccreanga.various.mapdb.Common.newPath;
import static com.ccreanga.various.mapdb.Common.rand;

import com.ccreanga.various.mapdb.Common;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.mapdb.DBMaker;
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
public class Serializer {

    static int no = 10_000_000;
    static Message[] messages = new Message[no];

    @Setup(Level.Trial)
    public static void fillMemoryMappedMap() throws IOException {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = new Message(i, (int)(Math.random()*10), ("test message " + i).getBytes(), System.currentTimeMillis());
        }
    }

    @Benchmark
    @Threads(1)
    public static void serializeKryo() {
        Kryo kryo = new Kryo();
        Output output = new Output(128,1024);
        for (Message message : messages) {
            kryo.writeObject(output, message);
            output.reset();
        }
    }

    @Benchmark
    @Threads(1)
    public static void serializeCustom() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(128);
        for (Message message : messages) {
            message.writeExternal(out);
            out.reset();
        }
    }


    public static void main(String[] args) throws RunnerException {
        Options opt;

        opt = new OptionsBuilder()
            .include(Serializer.class.getSimpleName())
            .verbosity(VerboseMode.NORMAL)
            .build();
        new Runner(opt).run();

    }
}
