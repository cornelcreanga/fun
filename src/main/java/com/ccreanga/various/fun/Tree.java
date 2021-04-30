package com.ccreanga.various.fun;

public class Tree<T> {

    private T value;
    private Tree<T> left;
    private Tree<T> right;

    public Tree(T value, Tree<T> left, Tree<T> right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }


    public Tree(T value) {
        this.value = value;
    }

    public static <T> int height(Tree<T> root) {
        if (root == null)
            return 0;
        return height(root, 0);
    }


    private static <T> int height(Tree<T> root, int currentValue) {
        if (root == null)
            return currentValue;
        int left = height(root.left, currentValue + 1);
        int right = height(root.right, currentValue + 1);
        return Math.max(left, right);
    }

    public static <T> boolean isBalanced(Tree<T> root){
        if (root == null)
            return true;
        int hleft = height(root.left);
        int hright = height(root.right);
        if (Math.abs(hleft-hright)>1)
            return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    /**
     * 1
     *
     * @param args
     */

    public static void main(String[] args) {
        Tree<Integer> rootUnbalanced = new Tree<>(10, new Tree<>(20), new Tree<>(25, new Tree<>(10), new Tree<>(34, new Tree<>(2), null)));
        Tree<Integer> rootBalanced = new Tree<>(10, new Tree<>(20), new Tree<>(25, new Tree<>(10), new Tree<>(34)));
        System.out.println(Tree.height(rootUnbalanced));
        System.out.println(Tree.isBalanced(rootUnbalanced));
        System.out.println(Tree.isBalanced(rootBalanced));
    }
}
