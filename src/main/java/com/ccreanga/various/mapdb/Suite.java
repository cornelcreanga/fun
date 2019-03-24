package com.ccreanga.various.mapdb;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.VerboseMode;

public class Suite {

    public static void main(String[] args) throws RunnerException {

//        Options opt = new OptionsBuilder()
//                .include(JdkMapBenchmark.class.getSimpleName())
//                .verbosity(VerboseMode.NORMAL)
//                .build();
//        new Runner(opt).run();

        Options opt = new OptionsBuilder()
            .include(MapDbBTreeBenchmark.class.getSimpleName())
            .verbosity(VerboseMode.NORMAL)
            .build();
        new Runner(opt).run();

//        opt = new OptionsBuilder()
//                .include(MapDbHTreeBenchmark.class.getSimpleName())
//                .verbosity(VerboseMode.NORMAL)
//                .build();
//        new Runner(opt).run();
//
//        opt = new OptionsBuilder()
//                .include(MapDbSTableBenchmark.class.getSimpleName())
//                .verbosity(VerboseMode.NORMAL)
//                .build();
//        new Runner(opt).run();
    }
}
