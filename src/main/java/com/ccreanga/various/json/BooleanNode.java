package com.ccreanga.various.json;

public class BooleanNode extends Node{
  public final static BooleanNode TRUE = new BooleanNode(true);
  public final static BooleanNode FALSE = new BooleanNode(false);

  private final boolean value;

  public BooleanNode(boolean v) { value = v; }
}
