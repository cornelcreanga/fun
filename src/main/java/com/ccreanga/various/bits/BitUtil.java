package com.ccreanga.various.bits;

public class BitUtil {

    public static byte noOfBits(long no){
        byte bits = 0;
        while(no>0){
            bits++;
            no=no>>1;
        }

        return bits;
    }


    public static byte getBit(long no,byte position){
        return (byte) ((no>>position)&1);
    }

    public static long resetBit(long no,byte position){
        return setBitValue(no,position, (byte) 0);
    }

    public static long setBit(long no,byte position){
        return setBitValue(no,position, (byte) 1);
    }

    private static long setBitValue(long no,byte position,byte value){
        long mask = ~ (1<<position);
        return (no&mask) | (value<<position);
    }

}
