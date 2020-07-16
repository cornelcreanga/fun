package com.ccreanga.various.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParseText {


  public void parseStream(JsonFactory jfactory, byte[] fileContent, JsonElement root, StringBuilder sb) throws Exception{
    JsonParser parser = jfactory.createParser(fileContent);
    parseRecursiveStream(parser,root,sb);
  }

  public void parseRecursiveStream(JsonParser parser, JsonElement current, StringBuilder sb) throws Exception{
    JsonToken token;
    String name = "";
    while ((token = parser.nextToken())!=null) {
      System.out.println(token.toString());
      switch (token){
        case START_OBJECT:
          sb.append('{');
//          JsonElement child = new JsonElement();
//          root.addChild(name,child);
          parseRecursiveStream(parser,current,sb);
          break;
        case END_OBJECT:
          if (sb.charAt(sb.length()-1)==',')
            sb.deleteCharAt(sb.length()-1);
          sb.append("},");
          return;
        case START_ARRAY:
          sb.append('[');
          break;
        case END_ARRAY:
          if (sb.charAt(sb.length()-1)==',')
            sb.deleteCharAt(sb.length()-1);
          sb.append("],");
          break;
        case FIELD_NAME:
          name = parser.getCurrentName();
          sb.append('"').append(parser.getCurrentName()).append('"');
          sb.append(':');
          break;
        case VALUE_EMBEDDED_OBJECT:
        case VALUE_STRING:
          sb.append('"').append(parser.getValueAsString()).append('"').append(",");
          break;
        case VALUE_NUMBER_INT:
          sb.append(parser.getBigIntegerValue()).append('"');
          break;
        case VALUE_NUMBER_FLOAT:
          sb.append(parser.getDecimalValue()).append('"');
          break;
        case VALUE_TRUE:
          sb.append(parser.getBooleanValue()).append('"');
          break;
        case VALUE_FALSE:
          sb.append(parser.getBooleanValue()).append('"');
          break;
        case VALUE_NULL:
          sb.append("null,");
          break;

      }

    }


  }

  public String parseJsonAsStream(JsonFactory jfactory, byte[] fileContent) throws IOException {
    JsonParser parser = jfactory.createParser(fileContent);
    JsonToken token;
    StringBuilder sb = new StringBuilder((int)(fileContent.length*1.5));
    while ((token = parser.nextToken())!=null) {
      switch (token){
        case START_OBJECT:
          sb.append('{');
          break;
        case END_OBJECT:
          if (sb.charAt(sb.length()-1)==',')
            sb.setLength(sb.length()-1);

          sb.append('}').append(',');
          break;
        case START_ARRAY:
          sb.append('[');
          break;
        case END_ARRAY:
          if (sb.charAt(sb.length()-1)==',')
            sb.setLength(sb.length()-1);
          sb.append(']').append(',');
          break;
        case VALUE_EMBEDDED_OBJECT:
        case VALUE_STRING:
          sb.append('"').append(parser.getValueAsString()).append('"').append(',');
          break;
        case VALUE_NUMBER_INT:
          sb.append(parser.getIntValue()).append('"');
          break;
        case VALUE_NUMBER_FLOAT:
          sb.append(parser.getFloatValue()).append('"');
          break;
        case VALUE_TRUE:
          sb.append(parser.getBooleanValue()).append('"');
          break;
        case VALUE_FALSE:
          sb.append(parser.getBooleanValue()).append('"');
          break;
        case VALUE_NULL:
          sb.append("null,");
          break;

      }

    }
    sb.deleteCharAt(sb.length()-1);
    return null;
  }

  public String parseJsonWithMapper(ObjectMapper mapper,byte[] fileContent) throws IOException {
    JsonNode root = mapper.readTree(fileContent);
    //return new String(mapper.writeValueAsBytes(root));
//    return root.toString();
    return null;
  }

  public static void main(String[] args) throws Exception {

    String file = "/home/cornel/projects/fun/src/main/java/com/ccreanga/various/json/r/medium.json";
    byte[] fileContent = Files.readAllBytes(Paths.get(file));
    ObjectMapper mapper = new ObjectMapper();
/**    JsonParseText benchmark = new JsonParseText();
    ObjectMapper mapper = new ObjectMapper();
    JsonFactory jfactory = new JsonFactory();

    System.out.println(benchmark.parseJsonAsStream(jfactory, fileContent));
    for (int i = 0; i < 100000; i++) {
      benchmark.parseJsonAsStream(jfactory, fileContent);
    }

    for (int i = 0; i < 100000; i++) {
      benchmark.parseJsonWithMapper(mapper,fileContent);
    }
    System.gc();
    long t1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      benchmark.parseJsonAsStream(jfactory, fileContent);
    }
    long t2 = System.currentTimeMillis();
    System.out.println(t2-t1);
    System.gc();
    t1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      benchmark.parseJsonWithMapper(mapper,fileContent);
    }
    t2 = System.currentTimeMillis();
    System.out.println(t2-t1);

    Deserializer deserializer = new Deserializer();

    for (int i = 0; i < 100000; i++) {
      JsonParser p = jfactory.createParser(fileContent);
      JsonToken t = p.getCurrentToken();
      if (t == null) {
        t = p.nextToken();
        if (t == null) { // [databind#1406]: expose end-of-input as `null`
        }
      }
      Node node = deserializer.deserializeObject(p, new StringBuilder());
    }

    System.gc();
    t1 = System.currentTimeMillis();
    for (int i = 0; i < 1000000; i++) {
      JsonParser p = jfactory.createParser(fileContent);
      JsonToken t = p.getCurrentToken();
      if (t == null) {
        t = p.nextToken();
        if (t == null) { // [databind#1406]: expose end-of-input as `null`
        }
      }
      Node node = deserializer.deserializeObject(p, new StringBuilder());

    }
    t2 = System.currentTimeMillis();
    System.out.println(t2-t1);**/


    Deserializer deserializer = new Deserializer();
    JsonFactory jfactory = new JsonFactory();
    JsonParser p = jfactory.createParser(fileContent);
    JsonToken t = p.getCurrentToken();
    if (t == null) {
      t = p.nextToken();
      if (t == null) { // [databind#1406]: expose end-of-input as `null`
      }
    }
    Node node = deserializer.deserializeObject(p, new StringBuilder());
    System.out.println("done");


//    JsonNode root = mapper.readTree(fileContent);
//
//    for (int i = 0; i < 10000; i++) {
//      root.toString();
//    }
//    System.gc();
//    long t1 = System.currentTimeMillis();
//    for (int i = 0; i < 300000; i++) {
//      root.toString();
//    }
//
//    long t2 = System.currentTimeMillis();
//    System.out.println(t2-t1);
//
//    for (int i = 0; i < 10000; i++) {
//      mapper.writeValueAsBytes(root);
//    }
//    System.gc();
//    t1 = System.currentTimeMillis();
//    for (int i = 0; i < 300000; i++) {
//      mapper.writeValueAsBytes(root);
//    }
//
//    t2 = System.currentTimeMillis();
//    System.out.println(t2-t1);

    //return new String(mapper.writeValueAsBytes(root));
//    return root.toString();

  }

}
