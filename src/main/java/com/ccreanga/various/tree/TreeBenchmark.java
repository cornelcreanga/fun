package com.ccreanga.various.tree;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TreeBenchmark {
    static int counter = 1;
    static BinaryTree tree = buildTree();

    public static void _buildTree(Node parent, int level) {
        if (level == 20)
            return;
        Node left = new Node(counter++);
        Node right = new Node(counter++);
        parent.setLeft(left);
        parent.setRight(right);
        _buildTree(left, level + 1);
        _buildTree(right, level + 1);

    }

    public static BinaryTree buildTree() {
        Node root = new Node(counter++);
        _buildTree(root, 0);
        System.out.println("Tree is built.");
        return new BinaryTree(root);
    }


    public static void commonAncestor(BinaryTree tree, Object value1, Object value2) {
        Node commonAncestor = tree.commonAncestor(value1, value2);
    }

    public static void commonAncestor2(BinaryTree tree, Object value1, Object value2) {
        ArrayList<Node> firstPath = tree.path(value1);
        ArrayList<Node> secondPath = tree.path(value2);
        Node commonAncestor = null;
        for (int i = 0; i < Math.min(firstPath.size(), secondPath.size()); i++) {
            if (firstPath.get(i).getData() != secondPath.get(i).getData()) {
                commonAncestor = firstPath.get(i - 1);
                break;
            }
        }

    }


    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void testCommonAncestor() {
        commonAncestor(tree, 3000, 1000000);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(value = 1)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5)
    public static void testCommonAncestor2() {
        commonAncestor2(tree, 3000, 1000000);
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(TreeBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }


}
