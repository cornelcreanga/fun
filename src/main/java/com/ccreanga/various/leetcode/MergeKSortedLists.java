package com.ccreanga.various.leetcode;

import java.util.*;

//https://leetcode.com/problems/merge-k-sorted-lists/
public class MergeKSortedLists {

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null)
            return null;
        PriorityQueue<ListNode> heap = new PriorityQueue<>(Comparator.comparingInt(o -> o.val));
        for (ListNode listNode : lists) {
            if (listNode != null)
                heap.add(listNode);
        }

        ListNode head = null;
        ListNode current = null;
        while (true) {
            ListNode smallest = heap.poll();
            if (smallest == null)
                break;
            if (head == null) {
                head = new ListNode(smallest.val, smallest.next);
                current = head;
            } else {
                current.next = new ListNode(smallest.val, smallest.next);
                current = current.next;
            }
            if (smallest.next != null)
                heap.add(smallest.next);
        }
        return head;
    }

    public static List<Integer> extractIntList(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode current = head;
        do {
            list.add(current.val);
            current = current.next;
        } while (current != null);
        return list;
    }


}
