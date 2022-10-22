package com.ccreanga.various;

import org.ehcache.sizeof.SizeOf;

import java.nio.ByteBuffer;
import java.util.UUID;

public class SizeOfObjects {

    public static void main(String[] args) {
        SizeOf sizeOf = SizeOf.newInstance();
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        byte[] b = new byte[1];
        System.out.println(sizeOf.deepSizeOf(uuid));
        System.out.println(sizeOf.deepSizeOf(s));
        System.out.println(sizeOf.deepSizeOf(b));

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
