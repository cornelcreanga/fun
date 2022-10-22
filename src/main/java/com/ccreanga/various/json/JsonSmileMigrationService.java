package com.ccreanga.various.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;

import java.io.ByteArrayOutputStream;

public class JsonSmileMigrationService {


    public static byte[] convertToSmile(byte[] json, JsonFactory jsonFactory, SmileFactory smileFactory) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try // try-with-resources
                (
                        JsonGenerator jg = smileFactory.createGenerator(bos);
                        JsonParser jp = jsonFactory.createParser(json)
                ) {
            while (jp.nextToken() != null) {
                jg.copyCurrentEvent(jp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    public static byte[] convertToJson(byte[] smile, JsonFactory jsonFactory, SmileFactory smileFactory) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try // try-with-resources
                (
                        JsonParser sp = smileFactory.createParser(smile);
                        JsonGenerator jg = jsonFactory.createGenerator(bos)
                ) {
            while (sp.nextToken() != null) {
                jg.copyCurrentEvent(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }
}


