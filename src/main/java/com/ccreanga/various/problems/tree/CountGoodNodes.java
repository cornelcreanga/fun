package com.ccreanga.various.problems.tree;

import java.util.ArrayList;
import java.util.List;

public class CountGoodNodes {


  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  public static int goodNodes(TreeNode root) {
    if (root == null)
      return 0;
    return goodNodesInternal(root, new ArrayList<>());
  }

  public static int goodNodesInternal(TreeNode root, List<Integer> path) {
    if (root == null)
      return 0;
    System.out.println(path.toString());

    int val = 0;
    if (isMax(path, root.val)) {
      val = 1;
    }
    List<Integer> leftPath = new ArrayList<>(path);
    leftPath.add(root.val);
    List<Integer> rightPath = new ArrayList<>(path);
    rightPath.add(root.val);

    return val + goodNodesInternal(root.left, leftPath) + goodNodesInternal(root.right, rightPath);
  }

  public static boolean isMax(List<Integer> list, int i) {
    for (Integer integer : list) {
      if (i < integer)
        return false;
    }
    return true;
  }

  public static void main(String[] args) {
    TreeNode root1 = new TreeNode(3, new TreeNode(1, new TreeNode(3), null), new TreeNode(4, new TreeNode(1), new TreeNode(5)));

    System.out.println(goodNodes(root1));

  }
}
