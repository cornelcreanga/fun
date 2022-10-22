package com.ccreanga.various;

public class Test {


  public static void main(String[] args) {

    String name = "java.sdf.util.ArrayList";
    String[] testSplit = name.split("\\.");
    if (testSplit.length > 2) {
      String testClass = testSplit[testSplit.length - 1].toUpperCase();
      System.out.println(testClass);
    }
  }

}
