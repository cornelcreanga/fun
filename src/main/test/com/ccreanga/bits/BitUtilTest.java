package com.ccreanga.bits;

import com.ccreanga.various.bits.BitUtil;
import org.junit.Assert;
import org.junit.Test;


public class BitUtilTest {

    @Test
    public void testGetBit() throws Exception {
        Assert.assertEquals(1, BitUtil.getBit(5, (byte) 0));
        Assert.assertEquals(BitUtil.getBit(5, (byte)1), 0);
        Assert.assertEquals(BitUtil.getBit(5, (byte)2), 1);
    }

    @Test
    public void testReSetBit() throws Exception {
        Assert.assertEquals(BitUtil.resetBit(5, (byte)1), 5);
        Assert.assertEquals(BitUtil.resetBit(9, (byte)1), 9);
        Assert.assertEquals(BitUtil.resetBit(9, (byte)0), 8);
        Assert.assertEquals(BitUtil.resetBit(9, (byte)3), 1);
    }

    @Test
    public void testSetBit() throws Exception {
        Assert.assertEquals(BitUtil.setBit(9, (byte)1), 11);
        Assert.assertEquals(BitUtil.setBit(9, (byte)2), 13);//1001
        Assert.assertEquals(BitUtil.setBit(16, (byte)0), 17);
    }

    @Test
    public void testNoOfBits() throws Exception {
        Assert.assertEquals(BitUtil.noOfBits(0), 0);
        Assert.assertEquals(BitUtil.noOfBits(255), 8);
    }
}