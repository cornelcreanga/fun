package com.ccreanga.various.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;
import com.google.common.io.ByteStreams;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Smile {

    public static void main(String[] args) throws Exception{

        ObjectMapper jsonMapper = new ObjectMapper();
        SmileFactory factory = new SmileFactory();
        factory.enable(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES);
        ObjectMapper smileMapper = new ObjectMapper(factory);

        long jsonLength=0, smileLength=0;
        File folder = new File("/home/cornel/Downloads/0104245f-c017-11e9-bac6-1d971f3632a6");
        File[] entries = folder.listFiles();
        for (File entry : entries) {
            byte[] jsonBytes = ByteStreams.toByteArray(new FileInputStream(entry));
            JsonNode json = jsonMapper.valueToTree(jsonBytes);
            byte[] smileData = smileMapper.writeValueAsBytes(json);
            jsonLength += jsonBytes.length;
            smileLength += smileData.length;
            //System.out.println(entry + "," + jsonBytes.length + "," + smileData.length);
            //System.out.println(((double)smileLength)/jsonLength);
        }

        System.out.println((double)smileLength/jsonLength);

    }
}
