package com.ccreanga.various.json;

import java.util.ArrayList;
import java.util.List;

public class ArrayNode extends Node {

  List<Node>_children = new ArrayList<>(10);

  public ArrayNode() {
  }

  public ArrayNode add(Node value) {
    _children.add(value);
    return this;
  }

}
