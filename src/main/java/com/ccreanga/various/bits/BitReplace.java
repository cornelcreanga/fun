package com.ccreanga.various.bits;

//given two long numbers insert the second one in the first one. The insertion point is defined by the bit position start
//todo - in fact it is a replacement rather than an insertion
public class BitReplace {

    public static long replace(long first, long second, byte start) {
        if ((start > 64) || (start < 0)) {
            throw new IllegalArgumentException(start + " should be between 0 and 64");
        }

        byte bitsSecond = BitUtil.noOfBits(second);
        if (bitsSecond > start) {
            throw new IllegalArgumentException(second + " will not fit into " + first + " from the position " + start);
        }
        byte counter = 0;
        for (byte i = (byte) (bitsSecond - 1); i >= 0; i--) {
            byte b = BitUtil.getBit(second, i);
            byte posToReplace = (byte) (start - counter);
            if (b == 1) {
                first = BitUtil.setBit(first, posToReplace);
            } else {
                first = BitUtil.resetBit(first, posToReplace);
            }
            counter++;

        }
        return first;
    }

}
