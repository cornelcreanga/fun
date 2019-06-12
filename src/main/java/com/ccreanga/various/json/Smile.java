package com.ccreanga.various.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.google.common.io.ByteStreams;

public class Smile {

    public static void main(String[] args) throws Exception{
        byte[] jsonBytes =  ByteStreams.toByteArray(ParseJson.class.getResourceAsStream("/test.json"));
        ObjectMapper jsonMapper = new ObjectMapper();
        ObjectMapper smileMapper = new ObjectMapper(new SmileFactory());

        JsonNode json = jsonMapper.valueToTree(jsonBytes);
            byte[] smileData = smileMapper.writeValueAsBytes(json);

        System.out.println(jsonBytes.length);
        System.out.println(smileData.length);


    }
}
