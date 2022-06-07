package com.ccreanga.various;

import com.google.common.primitives.UnsignedBytes;
import org.ehcache.sizeof.SizeOf;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.UUID;

public class SizeOfObjects {

    public static void main(String[] args) {
        SizeOf sizeOf = SizeOf.newInstance();
        UUID uuid= UUID.randomUUID();
        String s = uuid.toString();
        byte[] b= new byte[19];
        System.out.println(sizeOf.deepSizeOf(s));
        System.out.println(sizeOf.deepSizeOf(SizeOfObjects.asBytes(uuid)));
        System.out.println(sizeOf.deepSizeOf(s.getBytes()));
        System.out.println(sizeOf.deepSizeOf(b));
        System.out.println(sizeOf.deepSizeOf(new Long(3)));

        Comparator<byte[]> bytesComparator = UnsignedBytes.lexicographicalComparator();
    }

    public static UUID asUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
