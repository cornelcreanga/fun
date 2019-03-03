package com.ccreanga.various.tree;


public class Node {

    private Object data;
    private Node left, right;

    public Node(Object value) {
        data = value;
        left = right = null;
    }

    public Node(Object data, Node left, Node right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public Node setLeft(Node node){
        left = node;
        return this;
    }

    public Node left(){
        return left;
    }

    public Node right(){
        return right;
    }


    public Node setRight(Node node){
        right = node;
        return this;
    }

    public Object getData() {
        return data;
    }
}
