package com.ccreanga.various.floating;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class SubnormalNumbers {

    static float[] x = new float[]{1.1f, 1.2f, 1.3f, 1.4f, 1.5f, 1.6f, 1.7f, 1.8f, 1.9f, 2.0f, 2.1f, 2.2f, 2.3f, 2.4f, 2.5f, 2.6f};
    static float[] z = new float[]{1.123f, 1.234f, 1.345f, 156.467f, 1.578f, 1.689f, 1.790f, 1.812f, 1.923f, 2.034f, 2.145f, 2.256f, 2.367f, 2.478f,
        2.589f, 2.690f};
    static float[] y = new float[x.length];

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void floats() {
        System.arraycopy(x, 0, y, 0, x.length);
        for (int j = 0; j < 10000000; j++) {
            for (int i = 0; i < 16; i++) {
                y[i] *= (x[i] / z[i]);
                y[i] = y[i] + 0.00001f;
            }
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void subnormalFloats() {
        System.arraycopy(x, 0, y, 0, x.length);
        for (int j = 0; j < 10000000; j++) {
            for (int i = 0; i < 16; i++) {
                y[i] *= (x[i] / z[i]);
            }
        }
    }


    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
            .include(SubnormalNumbers.class.getSimpleName())
            .build();

        new Runner(opt).run();
    }

}
