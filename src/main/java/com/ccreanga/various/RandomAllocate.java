package com.ccreanga.various;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import java.util.stream.IntStream;

public class RandomAllocate {
    static Object[] arr;

    public static void main(String... args) {
        System.out.println(VM.current().details());
        int size = Integer.parseInt(args[0]);
        arr = new Object[size];
        IntStream.range(0, size).parallel().forEach(x -> {
            arr[x] = new byte[(x % 20) + 1];
        });

        byte[] bytes = new byte[3];
        System.out.println(ClassLayout.parseInstance(bytes).toPrintable());
        System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());

    }
}
