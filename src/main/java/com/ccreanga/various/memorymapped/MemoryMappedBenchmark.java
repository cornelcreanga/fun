package com.ccreanga.various.memorymapped;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MemoryMappedBenchmark {

    private static int count = 50*1024*1024;
    private static File file;
    private static MappedByteBuffer out;
    static long counter = 0;

    private static void createFile() {
        System.out.println("create file");
        try{
            file = File.createTempFile("testmemorymapped","");
            file.deleteOnExit();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        Random random = new SecureRandom();
        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file))){
            for(int i=0;i<count/4;i++)
                bufferedOutputStream.write(random.nextInt());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        try (RandomAccessFile memoryMappedFile = new RandomAccessFile(file, "rw")) {
            out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    static{
        createFile();
    }

    @org.openjdk.jmh.annotations.Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 10)
    public static void testReadMemoryMapped(){
        for (int i = 0; i < count; i++) {
            counter += out.get(i);
        }
    }

    public static void main(String[] args) throws Exception {

        Options opt = new OptionsBuilder()
                .include(MemoryMappedBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
        System.out.println(counter);
    }

}
