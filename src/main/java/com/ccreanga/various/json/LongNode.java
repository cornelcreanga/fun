package com.ccreanga.various.json;

public class LongNode extends Node{
  protected long no;
  public LongNode() {
  }

  public LongNode(long no) {
    super();
    this.no = no;
  }

  public Node replace(String fieldName, Node value) {
    return children.put(fieldName, value);
  }
}
