package com.ccreanga.various.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TextJustificationTest {

    @Test
    public void testFullJustify() {
        String[] words1 = new String[]{"This", "is", "an", "example", "of", "text", "justification."};
        String[] words2 = new String[]{"What", "must", "be", "acknowledgment", "shall", "be"};
        String[] words3 = new String[]{"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain",
                "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"};

        List<String> list1 = new ArrayList<>();
        list1.add("This    is    an");
        list1.add("example  of text");
        list1.add("justification.  ");
        List<String> list2 = new ArrayList<>();
        list2.add("What   must   be");
        list2.add("acknowledgment  ");
        list2.add("shall be        ");
        List<String> list3 = new ArrayList<>();
        list3.add("Science  is  what we");
        list3.add("understand      well");
        list3.add("enough to explain to");
        list3.add("a  computer.  Art is");
        list3.add("everything  else  we");
        list3.add("do                  ");

        assertEquals(TextJustification.fullJustify(words1, 16), list1);
        assertEquals(TextJustification.fullJustify(words2, 16), list2);
        assertEquals(TextJustification.fullJustify(words3, 20), list3);
    }

}