package com.ccreanga.various.lists.linked;

import java.util.function.Consumer;

public class LinkList {
    Node head; // head of list
    Node last;
    int count = 0;

    /* Linked list Node*/
    class Node {
        Object data;
        Node next;

        Node(Object d) { data = d; }
    }

    public LinkList() {
    }

    public void add(Object data){
        if (head==null){
            head = new Node(data);last=head;
        }else{
            last.next = new Node(data);
            last = last.next;
        }
    }

    public void iterate(Consumer<Node> consumer) {
        Node current = head;
        while (current != null) {
            consumer.accept(current);
            // Go to next node
            current = current.next;
        }
    }

    public void reverse(){
        Node prev = null;
        Node current = head;
        Node next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }


    public static void main(String[] args) {
        LinkList list = new LinkList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.iterate(node -> System.out.println(node.data));
        list.reverse();
        System.out.println();
    }
}
