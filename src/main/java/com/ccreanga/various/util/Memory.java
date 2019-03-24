package com.ccreanga.various.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import sun.misc.Unsafe;

public class Memory {

    static final Unsafe unsafe = getUnsafe();
    static final boolean is64bit = true;

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static List<String> getAddresses(Object... objects) {
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        ArrayList<String> list = new ArrayList<>(objects.length * 16);
        String address = "";
        switch (scale) {
            case 4:
                long factor = is64bit ? 8 : 1;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                address += Long.toHexString(i1);
                list.add(address);
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last) {
                        address += (", +" + Long.toHexString(i2 - last));
                    } else {
                        address += (", -" + Long.toHexString(last - i2));
                    }
                    last = i2;
                    list.add(address);
                }
                break;
            case 8:
                throw new UnsupportedOperationException("array scale" + scale + " is not supported");
        }

        return list;
    }

}
