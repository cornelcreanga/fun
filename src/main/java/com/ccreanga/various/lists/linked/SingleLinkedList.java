package com.ccreanga.various.lists.linked;

import java.util.function.Consumer;

public class SingleLinkedList<T> {
    Node<T> head; // head of list
    Node<T> last;
    int count = 0;

    public SingleLinkedList() {
    }

    public Node<T> getHead() {
        return head;
    }

    public void add(T data){
        if (head==null){
            head = new Node<>(data);
            last=head;
        }else{
            last.next = new Node<>(data);
            last = last.next;
        }
    }

    public void iterate(Consumer<Node<T>> consumer) {
        Node<T> current = head;
        while (current != null) {
            consumer.accept(current);
            // Go to next node
            current = current.next;
        }
    }

    public void reverse(){
        Node<T> prev = null;
        Node<T> current = head;
        Node<T> next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }


    public static void main(String[] args) {
        SingleLinkedList<String> list = new SingleLinkedList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.iterate(node -> System.out.println(node.getData()));
        list.reverse();
        System.out.println();
    }
}
