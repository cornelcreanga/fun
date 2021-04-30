package com.ccreanga.various.problems;

import com.ccreanga.various.problems.linkedlist.SingleListNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MergeKSortedListsTest {

    @Test
    public void mergeKLists() {
        SingleListNode<Integer> node1 = new SingleListNode<>(10, new SingleListNode<>(20, new SingleListNode<>(30, null)));
        SingleListNode<Integer> node2 = new SingleListNode<>(25, new SingleListNode<>(40, new SingleListNode<>(50, null)));
        SingleListNode<Integer> node3 = new SingleListNode<>(35, new SingleListNode<>(45, new SingleListNode<>(60, null)));
        SingleListNode<Integer>[] nodes = new SingleListNode[3];
        nodes[0] = node1;
        nodes[1] = node2;
        nodes[2] = node3;
        SingleListNode<Integer> sorted = MergeKSortedLists.mergeKLists(nodes, Comparator.comparing(o -> o.val));
        List<Integer> expected = Arrays.asList(10, 20, 25, 30, 35, 40, 45, 50, 60);

        assertEquals(MergeKSortedLists.extractIntList(sorted), expected);

        node1 = new SingleListNode<>(100, null);
        node2 = new SingleListNode<>(25, new SingleListNode<>(40, new SingleListNode<>(50, null)));
        node3 = new SingleListNode<>(35, new SingleListNode<>(45, new SingleListNode<>(60, null)));

        sorted = MergeKSortedLists.mergeKLists(nodes, Comparator.comparing(o -> o.val));
        expected = Arrays.asList(25, 35, 40, 45, 50, 60, 100);

        assertEquals(MergeKSortedLists.extractIntList(sorted), expected);

        assertNull(MergeKSortedLists.mergeKLists(null, null));

        node1 = null;
        node2 = new SingleListNode<>(25, new SingleListNode<>(40, new SingleListNode<>(50, null)));
        node3 = new SingleListNode<>(35, new SingleListNode<>(45, new SingleListNode<>(60, null)));

        sorted = MergeKSortedLists.mergeKLists(nodes, Comparator.comparing(o -> o.val));
        expected = Arrays.asList(25, 35, 40, 45, 50, 60);

        assertEquals(MergeKSortedLists.extractIntList(sorted), expected);
    }

}
