package com.ccreanga.various.compression.huffman.experiments;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;
import org.xerial.snappy.Snappy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Warmup(iterations = 2)
@Measurement(iterations = 3)
public class CompressionBenchmark {

    public static final int ITERATIONS = 100;
    private static byte[] originalContent;
    private static byte[] snappy;
    private static byte[] lz4;
    private static LZ4Factory factory;

    @Setup(Level.Trial)
    public static void initialize() throws IOException {
        originalContent = Files.readAllBytes(Path.of("/home/cornel/seqfile.seq"));
        factory = LZ4Factory.fastestInstance();
        snappy = Snappy.compress(originalContent);
        ImmutablePair<byte[], Integer> data = lz4Data(originalContent);
        int lz4Length = data.getRight();
        lz4 = new byte[lz4Length];
        System.arraycopy(data.getLeft(), 0, lz4, 0, lz4Length);

    }

    @Benchmark
    public static void snappyCompress(Blackhole blackhole) {
        try {
            for (int i = 0; i < ITERATIONS; i++) {
                byte[] snappy = Snappy.compress(originalContent);
                blackhole.consume(snappy);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public static void snappyUncompress(Blackhole blackhole) {
        try {
            for (int i = 0; i < ITERATIONS; i++) {
                byte[] uncompressed = Snappy.uncompress(snappy);
                blackhole.consume(uncompressed);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public static void lz4Uncompress(Blackhole blackhole) {

        try {
            for (int i = 0; i < ITERATIONS; i++) {
                LZ4FastDecompressor decompressor = factory.fastDecompressor();
                byte[] uncompressed = new byte[originalContent.length];
                decompressor.decompress(lz4,uncompressed);
                blackhole.consume(uncompressed);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Benchmark
    public static void lz4Compress(Blackhole blackhole) {

        try {
            for (int i = 0; i < ITERATIONS; i++) {
                ImmutablePair<byte[], Integer> data = lz4Data(originalContent);
                blackhole.consume(data);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ImmutablePair<byte[], Integer> lz4Data(byte[] data) {
        final int decompressedLength = data.length;
        LZ4Compressor compressor = factory.fastCompressor();//MAX_COMPRESSION_LEVEL
        //LZ4Compressor compressor = factory.highCompressor();
        int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
        byte[] compressed = new byte[maxCompressedLength];
        int compressedLength = compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);
        return new ImmutablePair<>(compressed, compressedLength);
    }

    public static void main(String[] args) throws Exception {
        Options opt;

        CompressionBenchmark.initialize();
        System.out.println("snappy.length:" + CompressionBenchmark.snappy.length);
        System.out.println("lz4.length:" + CompressionBenchmark.lz4.length);


        opt = new OptionsBuilder()
                .include(CompressionBenchmark.class.getSimpleName())
                .verbosity(VerboseMode.NORMAL)
                .build();
        new Runner(opt).run();

    }

}
