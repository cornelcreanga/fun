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
@Fork(value = 1, jvmArgs = {"-Xms1G", "-Xmx1G"})
public class RedisMapBenchmark {

    private static int count = 1000  * 1000;

    private static Jedis jedis;
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
    public static void testAddToMap() throws InterruptedException {
        int threads = 50;
        Thread[] writers = new Thread[threads];
        for (int i=0;i<threads;i++){
            writers[i] = new Thread(new ClientWriter(i*10000,(i+1)*10000));
        }

        for (int i=0;i<threads;i++){
            writers[i].start();
        }
        for (int i=0;i<threads;i++){
            writers[i].join();
        }
    }

    @Benchmark
    @Warmup(iterations = 1)
    @Measurement(iterations = 1)
    public static void testReadMemory() throws InterruptedException {
        int threads = 50;
        Thread[] readers = new Thread[threads];
        for (int i=0;i<threads;i++){
            readers[i] = new Thread(new ClientReader(i*10000,(i+1)*10000));
        }

        for (int i=0;i<threads;i++){
            readers[i].start();
        }
        for (int i=0;i<threads;i++){
            readers[i].join();
        }
    }

    private static class ClientWriter implements Runnable{

        int start,end;

        public ClientWriter(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            Jedis jedis = pool.getResource();
            for (int i=start;i<end;i++) {
                jedis.set(""+(long) i, "item" + i);
            }
            jedis.close();
        }
    }

    private static class ClientReader implements Runnable{

        int start,end;

        public ClientReader(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            Jedis jedis = pool.getResource();
            for (int i=start;i<end;i++) {
                jedis.get(""+(long) i);
            }
            jedis.close();
        }
    }

    public static void main(String[] args) throws Exception{


        Options opt = new OptionsBuilder()
                .include(RedisMapBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }

}
