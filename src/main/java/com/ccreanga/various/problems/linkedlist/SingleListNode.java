package com.ccreanga.various.problems.linkedlist;

public class SingleListNode<T> {
    public T val;
    public SingleListNode<T> next;

    public SingleListNode() {
    }

    public SingleListNode(T val) {
        this.val = val;
    }

    public SingleListNode(T val, SingleListNode<T> next) {
        this.val = val;
        this.next = next;
    }
}
