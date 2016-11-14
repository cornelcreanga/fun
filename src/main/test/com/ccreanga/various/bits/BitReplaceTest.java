package com.ccreanga.various.bits;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BitReplaceTest {
    @Test
    public void replace() throws Exception {
        Assert.assertEquals(BitReplace.replace(9,3, (byte) 3),13);
        /**
         * 1001
         * 1101
         *
         * 10000000000,
         *     10101
         * 10001010100
         *
         */
        Assert.assertEquals(BitReplace.replace(1024,21, (byte) 6),1108);

    }

}