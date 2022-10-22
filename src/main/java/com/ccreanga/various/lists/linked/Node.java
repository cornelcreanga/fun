package com.ccreanga.various.lists.linked;

/* Linked list Node*/
class Node<T> {
    T data;
    Node<T> next;

    Node(T d) { data = d; }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }
}
