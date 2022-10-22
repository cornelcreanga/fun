package com.ccreanga.various.json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Node {
  protected Map<String, Node> children;

  public Node() {
    children = new HashMap<>();
  }

  public Node replace(String fieldName, Node value) {
    return children.put(fieldName, value);
  }
}
