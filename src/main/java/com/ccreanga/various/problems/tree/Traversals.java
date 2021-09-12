package com.ccreanga.various.problems.tree;

import java.util.*;

public class Traversals {

  private static class Item<T extends Comparable<T>>{
    public Node<T> node;
    public int level;

    public Item(Node<T> node, int level) {
      this.node = node;
      this.level = level;
    }
  }

  public static <T extends Comparable<T>> List<Item<T>> breadthOrder(BinaryTree<T> tree){
    Queue<Item<T>> queue = new LinkedList<>();
    int level = 1;
    queue.add(new Item<T>(tree.root, level));
    List<Item<T>> treeItems = new ArrayList<>();
    while(!queue.isEmpty()){
      Item<T> head = queue.poll();
      treeItems.add(head);
      if (head.node.left() != null){
        queue.add(new Item<T>(head.node.left(), head.level+1));
      }
      if (head.node.right() != null){
        queue.add(new Item<T>(head.node.right(), head.level+1));
      }
    }
    return treeItems;
  }

//  private static void inOrderInternal(Node<T> node, Consumer<Node<T>> consumer) {
//    if (node == null) {
//      return;
//    }
//    inOrderInternal(node.left(), consumer);
//    consumer.accept(node);
//    inOrderInternal(node.right(), consumer);
//  }
//
//  public static <T> void inOrder(Consumer<Node<T>> consumer) {
//    inOrderInternal(root, consumer);
//  }
//
//  private static <T> void preOrderInternal(Node<T> node, Consumer<Node<T>> consumer) {
//    if (node == null) {
//      return;
//    }
//    consumer.accept(node);
//    preOrderInternal(node.left(), consumer);
//    preOrderInternal(node.right(), consumer);
//  }
//
//  public static <T> void preOrder(Consumer<Node<T>> consumer) {
//    preOrderInternal(root, consumer);
//  }
//
//
//  private static <T> Node<T> commonAncestorInternal(Node<T> parent, Object first, Object second) {
//    if (parent == null) {
//      return null;
//    }
//    if (parent.getData().equals(first) || (parent.getData().equals(second))) {
//      return parent;
//    }
//    Node<T> left = commonAncestorInternal(parent.left(), first, second);
//    Node<T> right = commonAncestorInternal(parent.right(), first, second);
//    if ((left != null) && (right != null)) {
//      return parent;
//    }
//    if (left != null) {
//      return left;
//    }
//    return right;
//
//  }

  public static void main(String[] args) {
    Node<Integer> node6 = new Node<>(6);
    Node<Integer> node5 = new Node<>(5);
    Node<Integer> node7 = new Node<>(7);
    Node<Integer> node8 = new Node<>(8);
    Node<Integer> node3 = new Node<>(3, node7, node8);

    Node<Integer> node4 = new Node<>(4, node6, null);
    Node<Integer> node2 = new Node<>(2, node4, node5);
    Node<Integer> root = new Node<>(1, node2, node3);
    BinaryTree<Integer> binaryTree = new BinaryTree<>(root);
    List<Item<Integer>> items = breadthOrder(binaryTree);
    Map<Integer, Integer> rightSide = new HashMap<>();
    for (Item<Integer> integerItem : items) {
      System.out.println(integerItem.node.data() +" - "+ integerItem.level);
      rightSide.put(integerItem.level, integerItem.node.data());
    }
    System.out.println(rightSide);

  }

}
