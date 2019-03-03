package com.ccreanga.various.tree;

import java.util.function.Consumer;

public class CommonAncestor {

    public static void main(String[] args) {
        Node node6 = new Node(6);
        Node node5 = new Node(5);
        Node node3 = new Node(3);
        Node node4 = new Node(4,node6,null);
        Node node2 = new Node(2,node4,node5);
        Node root = new Node(1, node2,node3);
        BinaryTree binaryTree = new BinaryTree(root);
        binaryTree.preOrder(node -> System.out.println(node.getData()));

    }

}


