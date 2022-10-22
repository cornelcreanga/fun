package com.ccreanga.various.problems.tree;


public class Node<T extends Comparable<T>> {

    private T data;
    private Node<T> left, right;

    public Node(T value) {
        data = value;
        left = right = null;
    }

    public Node(T data, Node<T> left, Node<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Node<T> setLeft(Node<T> node) {
        left = node;
        return this;
    }

    public Node<T> left() {
        return left;
    }

    public Node<T> right() {
        return right;
    }


    public Node<T> setRight(Node<T> node) {
        right = node;
        return this;
    }

    public T data() {
        return data;
    }
}
