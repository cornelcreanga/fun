package com.ccreanga.various.cycle;

import java.util.HashMap;
import java.util.Map;

public class CycleDetection {

    public static int mapBasedSolution(int[] A) {
        int count = 0;
        int i = 0;
        int solution = 0;
        boolean notFinished = true;
        final Map<Integer, Integer> map = new HashMap<>();
        do {
            count++;
            if (!map.containsKey(A[i])) {
                map.put(A[i], count);
                i = A[i];
            } else {
                solution = count - map.get(A[i]);
                notFinished = false;
            }
        } while (notFinished);
        return solution;
    }

    public static int tortoiseAndHare(int[] a) {
        int t = a[0], h = a[a[0]];
        while (t != h) {
            t = a[t];
            h = a[a[h]];
        }

//        int mu = 0;
//        t = 0;
//        while (t != h){
//            t = a[t];
//            h = a[h];
//            mu++;
//        }

        int counter = 1;
        h = a[t];
        while (t != h) {
            h = a[h];
            counter++;
        }

        return counter;
    }


    public static void main(String[] args) {
        int size = 100000;
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            int pos = (int) (Math.random() * size);
            array[i] = pos;
        }
        //crude benchmark
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            mapBasedSolution(array);
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);


        t1 = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            tortoiseAndHare(array);
        }
        t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

        System.out.println(mapBasedSolution(array));
        System.out.println(tortoiseAndHare(array));


    }


}
