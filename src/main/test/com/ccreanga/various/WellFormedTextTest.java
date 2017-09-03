package com.ccreanga.various;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class WellFormedTextTest {
    @Test
    public void isWellFormed() throws Exception {
        WellFormedText wellFormedText = new WellFormedText();
        Assert.assertTrue(wellFormedText.isWellFormed("abc"));
        Assert.assertTrue(wellFormedText.isWellFormed("a[()]bc"));
        Assert.assertTrue(wellFormedText.isWellFormed("a[]()bc"));
        Assert.assertTrue(wellFormedText.isWellFormed("a[[]()]bc"));
        Assert.assertFalse(wellFormedText.isWellFormed("abc)("));
        Assert.assertFalse(wellFormedText.isWellFormed("abc[)(]"));
        //Assert.assertFalse(wellFormedText.isWellFormed("abc[(])")); - todo - is it well formed?
        Assert.assertTrue(wellFormedText.isWellFormed2("abc"));
        Assert.assertTrue(wellFormedText.isWellFormed2("a[()]bc"));
        Assert.assertTrue(wellFormedText.isWellFormed2("a[]()bc"));
        Assert.assertTrue(wellFormedText.isWellFormed2("a[[]()]bc"));
        Assert.assertFalse(wellFormedText.isWellFormed2("abc)("));
        Assert.assertFalse(wellFormedText.isWellFormed2("abc[)(]"));

    }

}