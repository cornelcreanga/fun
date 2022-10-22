package com.ccreanga.various.problems.linkedlist;

public interface Queue<T> {

  boolean isEmpty();

  T dequeue();

  void enqueue(T item);

}
