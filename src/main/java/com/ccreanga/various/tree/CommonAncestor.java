package com.ccreanga.various.tree;

import java.util.List;
import java.util.function.Consumer;

public class CommonAncestor {

    public static void main(String[] args) {
        Node node6 = new Node(6);
        Node node5 = new Node(5);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node3 = new Node(3,node7,node8);

        Node node4 = new Node(4,node6,null);
        Node node2 = new Node(2,node4,node5);
        Node root = new Node(1, node2,node3);
        BinaryTree binaryTree = new BinaryTree(root);
        //binaryTree.preOrder(node -> System.out.println(node.getData()));
        //System.out.println(binaryTree.commonAncestor(node7,node8).getData());
        printList(binaryTree.path(6));
    }


    private static void printList(List<Node> l){
        for (int i = 0; i < l.size(); i++) {
            Object o = l.get(i);
            System.out.println(((Node) o).getData());
        }
    }

}


