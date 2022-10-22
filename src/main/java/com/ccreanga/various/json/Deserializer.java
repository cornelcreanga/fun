package com.ccreanga.various.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import java.io.IOException;

public class Deserializer {

  protected final Node deserializeObject(JsonParser p, StringBuilder sb)  throws IOException
  {
    final ObjectNode node = new ObjectNode();
    String key = p.nextFieldName();
    for (; key != null; key = p.nextFieldName()) {
      Node value;
      JsonToken t = p.nextToken();
      if (t == null) { // can this ever occur?
        t = JsonToken.NOT_AVAILABLE; // can this ever occur?
      }
      switch (t.id()) {
        case JsonTokenId.ID_START_OBJECT:
          value = deserializeObject(p, sb);
          break;
        case JsonTokenId.ID_START_ARRAY:
          value = deserializeArray(p, sb);
          break;
        case JsonTokenId.ID_EMBEDDED_OBJECT:
          value = new BinaryNode();//todo
          break;
        case JsonTokenId.ID_STRING:
          value = new StringNode(p.getText());
          break;
        case JsonTokenId.ID_NUMBER_INT:
          NumberType type = p.getNumberType();
          if (type== JsonParser.NumberType.INT)
            value = new IntNode(p.getIntValue());
          else if (type== NumberType.LONG)
            value = new LongNode(p.getLongValue());
          else throw new RuntimeException("this should never happen");
          break;
        case JsonTokenId.ID_TRUE:
          value = BooleanNode.TRUE;
          break;
        case JsonTokenId.ID_FALSE:
          value = BooleanNode.FALSE;
          break;
        case JsonTokenId.ID_NULL:
          value = NullNode.instance;
          break;
        default:
          value = deserializeAny(p, sb);
      }
      Node old = node.replace(key, value);
//      if (old != null) {
//        _handleDuplicateField(p, ctxt, nodeFactory,
//            key, node, old, value);
//      }
    }
    return node;
  }


  protected final Node deserializeArray(JsonParser p, StringBuilder sb) throws IOException {
    ArrayNode node = new ArrayNode();
    while (true) {
      JsonToken t = p.nextToken();
      switch (t.id()) {
        case JsonTokenId.ID_START_OBJECT:
          node.add(deserializeObject(p, sb));
          break;
        case JsonTokenId.ID_START_ARRAY:
          node.add(deserializeArray(p, sb));
          break;
        case JsonTokenId.ID_END_ARRAY:
          return node;
        case JsonTokenId.ID_EMBEDDED_OBJECT:
          node.add(new BinaryNode());
          break;
        case JsonTokenId.ID_STRING:
          node.add(new StringNode(p.getText()));
          break;
        case JsonTokenId.ID_NUMBER_INT:
          NumberType type = p.getNumberType();
          if (type== JsonParser.NumberType.INT)
            node.add(new IntNode(p.getIntValue()));
          else if (type== NumberType.LONG)
            node.add(new LongNode(p.getLongValue()));
          else throw new RuntimeException("this should never happen");
          break;
        case JsonTokenId.ID_TRUE:
          node.add(BooleanNode.TRUE);
          break;
        case JsonTokenId.ID_FALSE:
          node.add(BooleanNode.FALSE);
          break;
        case JsonTokenId.ID_NULL:
          node.add(NullNode.instance);
          break;
        default:
          node.add(deserializeAny(p, sb));
          break;
      }
    }
  }

  protected final Node deserializeAny(JsonParser p, StringBuilder sb) throws IOException
  {
    switch (p.getCurrentTokenId()) {
      case JsonTokenId.ID_END_OBJECT: // for empty JSON Objects we may point to this?
        return new ObjectNode();
      case JsonTokenId.ID_FIELD_NAME:
        return deserializeObjectAtName(p, sb);
      case JsonTokenId.ID_EMBEDDED_OBJECT:
        return new BinaryNode();//todo
      case JsonTokenId.ID_STRING:
        return new StringNode(p.getText());
      case JsonTokenId.ID_NUMBER_INT:
        NumberType type = p.getNumberType();
        if (type== JsonParser.NumberType.INT)
          return new IntNode(p.getIntValue());
        else if (type== NumberType.LONG)
          return new LongNode(p.getLongValue());
        else throw new RuntimeException("this should never happen");
      case JsonTokenId.ID_NUMBER_FLOAT:
        NumberType type2 = p.getNumberType();
        if (type2 == JsonParser.NumberType.BIG_DECIMAL)
          return new BigDecimalNode(p.getDecimalValue());
        if (type2 == JsonParser.NumberType.FLOAT) {
          return new FloatNode(p.getFloatValue());
        }
      case JsonTokenId.ID_TRUE:
        return BooleanNode.TRUE;
      case JsonTokenId.ID_FALSE:
        return BooleanNode.FALSE;
      case JsonTokenId.ID_NULL:
        return NullNode.instance;

            /* Caller checks for these, should not get here ever
        case JsonTokenId.ID_START_OBJECT:
            return deserializeObject(p, ctxt, nodeFactory);
        case JsonTokenId.ID_START_ARRAY:
            return deserializeArray(p, ctxt, nodeFactory);
            */


      // These states cannot be mapped; input stream is
      // off by an event or two

      //case END_OBJECT:
      //case END_ARRAY:
      default:
    }
    throw new RuntimeException("this should never happen");
  }

  protected final StringNode deserializeObjectAtName(JsonParser p, StringBuilder sb) throws IOException
  {
    final StringNode node = new StringNode();

    String key = p.getCurrentName();
    for (; key != null; key = p.nextFieldName()) {
      Node value;
      JsonToken t = p.nextToken();
      if (t == null) { // can this ever occur?
        t = JsonToken.NOT_AVAILABLE; // can this ever occur?
      }
      switch (t.id()) {
        case JsonTokenId.ID_START_OBJECT:
          value = deserializeObject(p, sb);
          break;
        case JsonTokenId.ID_START_ARRAY:
          value = deserializeArray(p, sb);
          break;
        case JsonTokenId.ID_EMBEDDED_OBJECT:
          value = new BinaryNode();
          break;
        case JsonTokenId.ID_STRING:
          value = new StringNode(p.getText());
          break;
        case JsonTokenId.ID_NUMBER_INT:
          NumberType type = p.getNumberType();
          if (type== JsonParser.NumberType.INT)
            value = new IntNode(p.getIntValue());
          else if (type== NumberType.LONG)
            value = new LongNode(p.getLongValue());
          else throw new RuntimeException("this should never happen");
          break;
        case JsonTokenId.ID_TRUE:
          value = BooleanNode.TRUE;
          break;
        case JsonTokenId.ID_FALSE:
          value = BooleanNode.FALSE;
          break;
        case JsonTokenId.ID_NULL:
          value = NullNode.instance;
          break;
        default:
          value = deserializeAny(p, sb);
      }
      Node old = node.replace(key, value);
//      if (old != null) {
//        _handleDuplicateField(p, ctxt, nodeFactory,
//            key, node, old, value);
//      }
    }
    return node;
  }
}
