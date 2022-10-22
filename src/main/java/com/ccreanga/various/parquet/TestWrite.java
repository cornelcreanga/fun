package com.ccreanga.various.parquet;

import com.google.common.io.ByteStreams;
import java.io.IOException;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.kitesdk.compat.Hadoop.Configuration;
import org.kitesdk.data.spi.JsonUtil;
import org.kitesdk.data.spi.filesystem.JSONFileReader;

public class TestWrite {

    public static void main(String[] args) throws IOException {

//        ByteStreams.toByteArray(Smile.class.getClassLoader().getResourceAsStream("test.json"));
//
//        Schema jsonSchema = JsonUtil.inferSchema(fs.open(source), "RecordName", 20);
//        try (JSONFileReader<Record> reader = new JSONFileReader<>(
//            fs.open(source), jsonSchema, Record.class)) {
//
//            reader.initialize();
//
//            try (ParquetWriter<Record> writer = AvroParquetWriter
//                .<Record>builder(outputPath)
//                .withConf(new Configuration())
//                .withCompressionCodec(CompressionCodecName.SNAPPY)
//                .withSchema(jsonSchema)
//                .build()) {
//                for (Record record : reader) {
//                    writer.write(record);
//                }
//            }
//        }
    }

}
