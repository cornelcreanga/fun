package com.ccreanga.various.json;

public class IntNode extends Node{

  protected int no;
  public IntNode() {
  }

  public IntNode(int no) {
    super();
    this.no = no;
  }

  public Node replace(String fieldName, Node value) {
    return children.put(fieldName, value);
  }

}
