package com.ccreanga.various.json;

public class BinaryNode extends Node{
  protected byte[] data;

  public BinaryNode() {
  }

  public BinaryNode(byte[] data) {
    super();
    this.data = data;
  }

  public Node replace(String fieldName, Node value) {
    return children.put(fieldName, value);
  }
}
