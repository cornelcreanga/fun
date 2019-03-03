package com.ccreanga.various.tree;

import java.util.function.Consumer;

public class BinaryTree {

    Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    private void _inOrder(Node node, Consumer<Node> consumer){
        if (node==null)
            return;
        _inOrder(node.left(),consumer);
        consumer.accept(node);
        _inOrder(node.right(),consumer);
    }

    public void inOrder(Consumer<Node> consumer){
        _inOrder(root,consumer);
    }

    private void _preOrder(Node node, Consumer<Node> consumer){
        if (node==null)
            return;
        consumer.accept(node);
        _preOrder(node.left(),consumer);
        _preOrder(node.right(),consumer);
    }

    public void preOrder(Consumer<Node> consumer){
        _preOrder(root,consumer);
    }

    public void breadthFirst(Consumer<Node> consumer){
        //https://www.geeksforgeeks.org/level-order-tree-traversal/
    }
}
