package com.ccreanga.various.tree;

import java.util.ArrayList;
import java.util.function.Consumer;

public class BinaryTree {

    Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    private void _inOrder(Node node, Consumer<Node> consumer) {
        if (node == null)
            return;
        _inOrder(node.left(), consumer);
        consumer.accept(node);
        _inOrder(node.right(), consumer);
    }

    public void inOrder(Consumer<Node> consumer) {
        _inOrder(root, consumer);
    }

    private void _preOrder(Node node, Consumer<Node> consumer) {
        if (node == null)
            return;
        consumer.accept(node);
        _preOrder(node.left(), consumer);
        _preOrder(node.right(), consumer);
    }

    public void preOrder(Consumer<Node> consumer) {
        _preOrder(root, consumer);
    }

    public void breadthFirst(Consumer<Node> consumer) {
        //https://www.geeksforgeeks.org/level-order-tree-traversal/
    }

    private Node _commonAncestor(Node parent, Object first, Object second) {
        if (parent == null)
            return null;
        if (parent.getData().equals(first) || (parent.getData().equals(second)))
            return parent;
        Node left = _commonAncestor(parent.left(), first, second);
        Node right = _commonAncestor(parent.right(), first, second);
        if ((left != null) && (right != null))
            return parent;
        if (left != null)
            return left;
        return right;

    }

    public Node commonAncestor(Object first, Object second) {
        return _commonAncestor(root, first, second);
    }

    private boolean _find(Node parent, Object value, ArrayList<Node> currentPath) {
        if (parent == null)
            return false;
        currentPath.add(parent);
        if (parent.getData().equals(value))
            return true;
        boolean left = _find(parent.left(), value, currentPath);
        if (left)
            return true;
        boolean right = _find(parent.right(), value, currentPath);
        if (right)
            return true;
        //not found
        currentPath.remove(parent);
        return false;

    }

    public ArrayList<Node> path(Object value) {
        ArrayList<Node> nodes = new ArrayList<>();
        _find(root, value, nodes);
        return nodes;
    }
}
