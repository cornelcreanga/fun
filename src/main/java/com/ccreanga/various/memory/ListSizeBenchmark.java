package com.ccreanga.various.memory;

import com.ccreanga.various.util.FormatUtil;
import com.koloboke.collect.map.hash.HashLongLongMaps;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongIterators;
import it.unimi.dsi.sux4j.util.EliasFanoLongBigList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.ehcache.sizeof.SizeOf;


public class ListSizeBenchmark {


    static int LIST_SIZE = 10_000_000;
    static int MAP_SIZE = 1_000_000;
    static SizeOf sizeOf = SizeOf.newInstance();

    private static void listSize(){
        List<Long> jdkList = new ArrayList<>(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            jdkList.add((long)(Math.random()*Short.MAX_VALUE));
        }
        long deepSize = sizeOf.deepSizeOf(jdkList);
        System.out.println(FormatUtil.readableSize(deepSize));

        LongArrayList fastUtilList = new LongArrayList(LIST_SIZE);
        for (int i = 0; i < LIST_SIZE; i++) {
            fastUtilList.add((long)(Math.random()*Short.MAX_VALUE));
        }
        deepSize = sizeOf.deepSizeOf(fastUtilList);
        System.out.println(FormatUtil.readableSize(deepSize));


        LongIterator longIterator = LongIterators.asLongIterator(jdkList.iterator());
        EliasFanoLongBigList succintList = new EliasFanoLongBigList(longIterator);
        deepSize = sizeOf.deepSizeOf(succintList);
        System.out.println(FormatUtil.readableSize(deepSize));
    }

    private static void mapSize(){
        Map<Long,Long> jdkMap = new HashMap<>(MAP_SIZE);
        for (int i = 0; i < MAP_SIZE; i++) {
            jdkMap.put((long)i,((long)(Math.random()*Short.MAX_VALUE)));
        }
        long deepSize = sizeOf.deepSizeOf(jdkMap);
        System.out.println(FormatUtil.readableSize(deepSize));

        Map<Long,Long> kolobokeMap = HashLongLongMaps.newMutableMap();
        for (int i = 0; i < MAP_SIZE; i++) {
            kolobokeMap.put((long)i,((long)(Math.random()*Short.MAX_VALUE)));
        }

        deepSize = sizeOf.deepSizeOf(kolobokeMap);
        System.out.println(FormatUtil.readableSize(deepSize));

        Long2LongOpenHashMap fastUtilMap = new Long2LongOpenHashMap(MAP_SIZE);
        for (int i = 0; i < MAP_SIZE; i++) {
            fastUtilMap.put((long)i,((long)(Math.random()*Short.MAX_VALUE)));
        }
        deepSize = sizeOf.deepSizeOf(fastUtilMap);
        System.out.println(FormatUtil.readableSize(deepSize));


    }

    public static void main(String args[]){
        listSize();
        mapSize();


    }

}
