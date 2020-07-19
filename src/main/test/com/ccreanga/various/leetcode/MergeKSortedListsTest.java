package com.ccreanga.various.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MergeKSortedListsTest {

    @Test
    public void mergeKLists() {
        ListNode node1 = new ListNode(10, new ListNode(20, new ListNode(30, null)));
        ListNode node2 = new ListNode(25, new ListNode(40, new ListNode(50, null)));
        ListNode node3 = new ListNode(35, new ListNode(45, new ListNode(60, null)));
        ListNode sorted = MergeKSortedLists.mergeKLists(new ListNode[]{node1, node2, node3});
        List<Integer> expected = Arrays.asList(10, 20, 25, 30, 35, 40, 45, 50, 60);

        assertEquals(MergeKSortedLists.extractIntList(sorted), expected);

        node1 = new ListNode(100, null);
        node2 = new ListNode(25, new ListNode(40, new ListNode(50, null)));
        node3 = new ListNode(35, new ListNode(45, new ListNode(60, null)));

        sorted = MergeKSortedLists.mergeKLists(new ListNode[]{node1, node2, node3});
        expected = Arrays.asList(25, 35, 40, 45, 50, 60, 100);

        assertEquals(MergeKSortedLists.extractIntList(sorted), expected);

        assertNull(MergeKSortedLists.mergeKLists(null));

        node1 = null;
        node2 = new ListNode(25, new ListNode(40, new ListNode(50, null)));
        node3 = new ListNode(35, new ListNode(45, new ListNode(60, null)));

        sorted = MergeKSortedLists.mergeKLists(new ListNode[]{node1, node2, node3});
        expected = Arrays.asList(25, 35, 40, 45, 50, 60);

        assertEquals(MergeKSortedLists.extractIntList(sorted), expected);
    }

}
