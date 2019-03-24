package com.ccreanga.various.strings;

import org.junit.Assert;
import org.junit.Test;

public class MaximumNumberTest {

    @Test
    public void computeMaximumNumber() throws Exception {
        String[] s = new String[]{"32", "324", "321"};
        Assert.assertEquals("32432321", MaximumNumber.computeMaximumNumber(s));

        s = new String[]{"32", "324", "321", "0", "5"};
        Assert.assertEquals("5324323210", MaximumNumber.computeMaximumNumber(s));

        boolean caught = false;
        try {
            s = new String[]{"32", "a"};
            MaximumNumber.computeMaximumNumber(s);
        } catch (RuntimeException e) {
            caught = true;
        }
        Assert.assertTrue(caught);

        caught = false;
        try {
            s = new String[]{"32", "-1"};
            MaximumNumber.computeMaximumNumber(s);
        } catch (RuntimeException e) {
            caught = true;
        }
        Assert.assertTrue(caught);

    }


}