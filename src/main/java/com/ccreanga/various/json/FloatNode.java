package com.ccreanga.various.json;

public class FloatNode extends Node{
  protected float no;

  public FloatNode() {
  }
  public FloatNode(float no) {
    super();
    this.no = no;
  }

  public Node replace(String fieldName, Node value) {
    return children.put(fieldName, value);
  }
}
