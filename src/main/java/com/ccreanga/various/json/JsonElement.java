package com.ccreanga.various.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonElement {

  private String nodeName;
  private List<JsonElement> subElements = null;
  private boolean isArray;

  public JsonElement(String nodeName) {
    this.nodeName = nodeName;
  }

  public void addChild(JsonElement child){
    if (subElements==null)
      subElements = new ArrayList<>(5);
    subElements.add(child);
  }

  public String getNodeName() {
    return nodeName;
  }

  public void setNodeName(String nodeName) {
    this.nodeName = nodeName;
  }


  public boolean isArray() {
    return isArray;
  }

  public void setArray(boolean array) {
    isArray = array;
  }

  /**
   def getSchema(node: JsonNode, prefix: String = ""): JsonElement = {
   val children = new mutable.HashMap[String, JsonElement]()
   if (node.isArray) {
   node.filter(_.isObject).map( nodeChild => {
   children ++= parseChildrenSchema(nodeChild, prefix)
   })
   }
   if (node.isObject) {
   children ++= parseChildrenSchema(node, prefix)
   }
   JsonElement(prefix, children, node.isArray)
   }

   def parseChildrenSchema(node: JsonNode, prefix: String = ""): mutable.HashMap[String, JsonElement] = {
   val children = new mutable.HashMap[String, JsonElement]()
   for (field: Entry[String, JsonNode] <- node.fields()) {
   val value = field.getValue
   children.put(field.getKey, getSchema(value, getFieldName(prefix, field.getKey)))
   }
   children
   }
   */
}
