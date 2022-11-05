package com.ccreanga.various;

import org.ehcache.sizeof.SizeOf;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.nio.ByteBuffer;
import java.util.UUID;

public class SizeOfObjects {

    public static class Test{
        int x;

        public Test(int x) {
            this.x = x;
        }
    }

    public static void main(String[] args) {
        SizeOf sizeOf = SizeOf.newInstance();
//        UUID uuid = UUID.randomUUID();
//        String s = uuid.toString();
//
//        System.out.println(sizeOf.deepSizeOf(new Object()));
//        System.out.println(sizeOf.deepSizeOf(new Test(2222)));
//        System.out.println(sizeOf.deepSizeOf(uuid));
//        System.out.println(sizeOf.deepSizeOf(s));

        System.out.println(VM.current().details());

        for (int i = 0; i < 20; i++) {
            System.out.println(ClassLayout.parseInstance(new byte[i]).instanceSize());
        }

/**# Running 64-bit HotSpot VM.
 # Using compressed oop with 0x0000001000000000 base address and 3-bit shift.
 # Using compressed klass with 0x0000000800000000 base address and 3-bit shift.

 # Running 64-bit HotSpot VM.
 # Using compressed oop with 3-bit shift.
 # Using compressed klass with 3-bit shift

 */

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
