package com.ccreanga.various.problems.linkedlist;

public class QueueLinkedList<T> implements Queue<T> {

  private SingleListNode<T> head = new SingleListNode<>();
  private SingleListNode<T> tail = head;

  @Override
  public boolean isEmpty() {
    return head == tail;
  }

  @Override
  public T dequeue() {
    if (tail == null)
      return null;
    T value = tail.val;
    tail = tail.next;
    if (tail == null)
      tail = head;
    return value;
  }

  @Override
  public void enqueue(T item) {
    head.val = item;
    head.next = new SingleListNode<>();
    head = head.next;
  }

  public static void main(String[] args) {
    QueueLinkedList<String> list = new QueueLinkedList<>();
    list.enqueue("a");
    list.enqueue("b");
    System.out.println(list.dequeue());
    System.out.println(list.dequeue());
    System.out.println(list.dequeue());
    list.enqueue("c");
    System.out.println(list.dequeue());
  }
}
