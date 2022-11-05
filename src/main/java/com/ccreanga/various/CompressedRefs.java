package com.ccreanga.various;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class CompressedRefs {

    static class MyClass {
        int x;
        public MyClass(int x) { this.x = x; }
        public int x() { return x; }
    }

    private MyClass o = new MyClass(42);

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public int access() {
        return o.x();
    }

}