package com.ccreanga.various.redis;

import com.ccreanga.various.memorymapped.MemoryBenchmark;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})

public class MapBenchmark {

    private static int count = 5 * 1024 * 1024;

    private static Jedis jedis;
    @Setup(Level.Trial)
    public static void createByteArray() {
        Jedis jedis = new Jedis("localhost");
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public static void testAddMap() {
        for (int i=0;i<count;i++) {
            jedis.set(""+(long) i, "item" + i);
        }
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public static void testReadMemory() {
        for (int i=0;i<count;i++) {
            jedis.get(""+(long) i);
        }
    }

    public static void main(String[] args) throws Exception{


        Options opt = new OptionsBuilder()
                .include(MapBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();

//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setTestOnBorrow(true);
//        config.setMaxIdle(1000);
//        config.setMaxTotal(1000);
//        config.setBlockWhenExhausted(true);
//        config.setMaxWaitMillis(180000);
//        JedisPool pool = new JedisPool(config, "172.17.0.2", 6379);
//
//        Jedis jedis = pool.getResource();
//        Jedis jedis = new Jedis("localhost");
//        //adding a new key
//        jedis.set("key", "value");
//        //getting the key value
//        System.out.println(jedis.get("key"));
    }

}
