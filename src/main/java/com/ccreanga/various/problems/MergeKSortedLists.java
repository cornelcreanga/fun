package com.ccreanga.various.problems;

import com.ccreanga.various.problems.linkedlist.SingleListNode;

import java.util.*;

//https://leetcode.com/problems/merge-k-sorted-lists/
public class MergeKSortedLists<T> {

    public static <T> SingleListNode<T> mergeKLists(SingleListNode<T>[] lists, Comparator<SingleListNode<T>> comparator) {
        if (lists == null)
            return null;
        PriorityQueue<SingleListNode<T>> heap = new PriorityQueue<>(comparator);
        for (SingleListNode<T> listNode : lists) {
            if (listNode != null)
                heap.add(listNode);
        }

        SingleListNode<T> head = null;
        SingleListNode<T> current = null;
        while (true) {
            SingleListNode <T>smallest = heap.poll();
            if (smallest == null)
                break;
            if (head == null) {
                head = new SingleListNode<T>(smallest.val, smallest.next);
                current = head;
            } else {
                current.next = new SingleListNode<T>(smallest.val, smallest.next);
                current = current.next;
            }
            if (smallest.next != null)
                heap.add(smallest.next);
        }
        return head;
    }

    public static <T> List<T> extractIntList(SingleListNode<T> head) {
        List<T> list = new ArrayList<>();
        SingleListNode<T> current = head;
        do {
            list.add(current.val);
            current = current.next;
        } while (current != null);
        return list;
    }


}
