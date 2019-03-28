package com.ccreanga.various.redis;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.util.concurrent.TimeUnit;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class RedisMapBenchmark {

    private static int count = 1000  * 1000;

    private static JedisPoolConfig config;
    private static JedisPool pool;


    @Setup(Level.Trial)
    public static void createByteArray() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setMaxIdle(1000);
        config.setMaxTotal(1000);
        config.setBlockWhenExhausted(true);
        config.setMaxWaitMillis(180000);
        pool = new JedisPool(config,"localhost", 6379);

    }

    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    @Threads(50)
    public static void testAddToMap(){
        Jedis jedis = pool.getResource();
        for(int i=0;i<1000;i++) {
            jedis.set(String.valueOf(i), "item"+i);

        }
        jedis.close();
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    @Threads(50)
    public static void testReadMemory() throws InterruptedException {
        Jedis jedis = pool.getResource();
        for(int i=0;i<1000;i++) {
            jedis.get(String.valueOf(i));
        }
        jedis.close();
    }

    public static void main(String[] args) throws Exception{


        Options opt = new OptionsBuilder()
                .include(RedisMapBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}
