package com.ccreanga.various.graph;

import java.util.*;

public class Node<T> {

    private T value;
    private List<Node<T>> neighbours;

    public Node(T value) {
        this.value = value;
        neighbours = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Node<T>> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Node<T>> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Node<T> neighbour) {
        this.neighbours.add(neighbour);
    }


    public static <T> boolean depthFirst(Node<T> init, T value) {
        Set<Node<T>> visited = new HashSet<>();
        return depthFirstInternal(init, value, visited);
    }

    public static<T> boolean breadthFirst(Node<T> init, T value) {
        Set<Node<T>> visited = new HashSet<>();
        return breadthFirstInternal(init, value, visited);

    }

    private static <T> boolean breadthFirstInternal(Node<T> init, T value, Set<Node<T>> visited) {
        System.out.println("visiting "+init.value);
        if (init.value.equals(value)) {
            return true;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        List<Node<T>> neighbours = init.getNeighbours();
        queue.addAll(neighbours);
        while(!queue.isEmpty()){
            Node<T> node = queue.poll();
            System.out.println("visiting "+node.value);
            if (node.value.equals(value)) {
                return true;
            }else{
                queue.addAll(node.getNeighbours());
            }
        }
        return false;
    }

    private static <T> boolean depthFirstInternal(Node<T> init, T value, Set<Node<T>> visited) {
        System.out.println("visiting "+init.value);
        if (init.value.equals(value)) {
            return true;
        }
        List<Node<T>> neighbours = init.getNeighbours();
        for (Node<T> node : neighbours) {
            if (!visited.contains(node)) {
                visited.add(node);
                if (depthFirstInternal(node, value, visited))
                    return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Node<String> first = new Node<>("1");
        Node<String> second = new Node<>("2");
        first.addNeighbour(second);
        first.addNeighbour(new Node<>("3"));
        first.addNeighbour(new Node<>("4"));
        second.addNeighbour(new Node<>("5"));
        System.out.println(Node.depthFirst(first, "5"));
        System.out.println(Node.breadthFirst(first, "5"));

    }

}
