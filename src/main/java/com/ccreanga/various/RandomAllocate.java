package com.ccreanga.various;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.FieldLayout;
import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

import java.util.SortedSet;
import java.util.stream.IntStream;

public class RandomAllocate {
    static Object[] arr;

    public static void main(String... args) {
        VirtualMachine vm = VM.current();
        System.out.println(vm.details());

//        int size = Integer.parseInt(args[0]);
//        arr = new Object[size];
//        IntStream.range(0, size).parallel().forEach(x -> {
//            arr[x] = new byte[(x % 20) + 1];
//        });
        /**
         OFF  SZ               TYPE DESCRIPTION               VALUE
         0   8                    (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
         8   4                    (object header: class)    0x00001228
         12   4                    (array length)            3
         12   4                    (alignment/padding gap)
         16  12   java.lang.Object Object;.<elements>        N/A
         28   4                    (object alignment gap)
         Instance size: 32 bytes
         Space losses: 4 bytes internal + 4 bytes external = 8 bytes total

         OFF  SZ               TYPE DESCRIPTION               VALUE
         0   8                    (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
         8   4                    (object header: class)    0x00001228
         12   4                    (array length)            3
         12   4                    (alignment/padding gap)
         16  24   java.lang.Object Object;.<elements>        N/A
         Instance size: 40 bytes
         Space losses: 4 bytes internal + 0 bytes external = 4 bytes total
         */

        System.out.println(ClassLayout.parseInstance(new Object[3]).toPrintable());
    }
}
/**
 java.lang.Object object internals:
 OFF  SZ   TYPE DESCRIPTION               VALUE
 0   8        (object header: mark)     0x0000000000000001 (non-biasable; age: 0)
 8   4        (object header: class)    0x00000e60
 12   4        (object alignment gap)



 */