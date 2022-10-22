package com.ccreanga.various.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;

public class Smile {

    public static void main(String[] args) throws Exception{

        ObjectMapper jsonMapper = new ObjectMapper();
        JsonFactory jsonFactory = new JsonFactory();
        SmileFactory smileFactory = new SmileFactory();
        smileFactory.enable(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES);
        smileFactory.disable(SmileGenerator.Feature.ENCODE_BINARY_AS_7BIT);
        ObjectMapper smileMapper = new ObjectMapper(smileFactory);

        long jsonLength=0, smileLength=0;
        File folder = new File("/home/cornel/Downloads/0104245f-c017-11e9-bac6-1d971f3632a6");
//        File folder = new File("/home/cornel/Downloads/json");
        File[] entries = folder.listFiles();
        long t1 = System.currentTimeMillis();
        for (File entry : entries) {
//            if (!entry.getName().endsWith(".json"))
//                continue;
            byte[] jsonBytes = ByteStreams.toByteArray(new FileInputStream(entry));
            byte[] smileData = JsonSmileMigrationService.convertToSmile(jsonBytes,jsonFactory,smileFactory);
            //Files.write(smileData, new File(entry.getPath()+"b"));
            jsonLength += jsonBytes.length;
            smileLength += smileData.length;
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
        System.out.println((double)smileLength/jsonLength);

    }
}
