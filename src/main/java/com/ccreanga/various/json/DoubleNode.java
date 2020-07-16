package com.ccreanga.various.json;

public class DoubleNode extends Node{
  protected double no;

  public DoubleNode() {
  }
  public DoubleNode(double no) {
    super();
    this.no = no;
  }

  public Node replace(String fieldName, Node value) {
    return children.put(fieldName, value);
  }
}
