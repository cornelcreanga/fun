package com.ccreanga.various.hadoop;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.compress.Lz4Codec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class SeqFileCompressionBenchmark {

    public static final int ITEMS = 256 * 1024;


    public static void main(String[] args) throws IOException {
        NullWritable key1 = NullWritable.get();
        Random fastRandom = new Random();
        long uploadTime = 0, t1, t2;
        long processTime = 0, w1, w2;
        char[] numbers = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        byte[][] data = new byte[ITEMS][1024];
        for (int j = 0; j < ITEMS; j++) {
            data[j] = RandomStringUtils.random(1024, 0, numbers.length, false, false, numbers, fastRandom).getBytes(StandardCharsets.UTF_8);
        }

        Lz4Codec lz4Codec = new Lz4Codec();
        Configuration config = new Configuration();
        config.set("io.compression.codec.lz4.use.lz4hc", "false");
        lz4Codec.setConf(config);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 1024);
        SequenceFile.Writer writer = SequenceFile.createWriter(new Configuration(),
                SequenceFile.Writer.keyClass(NullWritable.class),
                SequenceFile.Writer.valueClass(BytesWritable.class),
//                SequenceFile.Writer.compression(SequenceFile.CompressionType.NONE),
                    SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK, lz4Codec),
                SequenceFile.Writer.stream(new FSDataOutputStream(out, null)));

        for (int j = 0; j < ITEMS; j++) {
            writer.append(key1, new BytesWritable(data[j]));
        }
        writer.close();

        System.gc();
        w1 = System.currentTimeMillis();
        out = new ByteArrayOutputStream(1024 * 1024);
        writer = SequenceFile.createWriter(new Configuration(),
                SequenceFile.Writer.keyClass(NullWritable.class),
                SequenceFile.Writer.valueClass(BytesWritable.class),
//                SequenceFile.Writer.compression(SequenceFile.CompressionType.NONE),
                    SequenceFile.Writer.compression(SequenceFile.CompressionType.BLOCK, lz4Codec),
                SequenceFile.Writer.stream(new FSDataOutputStream(out, null)));

        for (int j = 0; j < ITEMS; j++) {
            writer.append(key1, new BytesWritable(data[j]));
        }
        writer.close();
        byte[] outArray = out.toByteArray();
        System.out.println(ITEMS*1024);
        System.out.println(outArray.length);
        w2 = System.currentTimeMillis();
        System.out.println(w2 - w1);

    }


}
