package com.ccreanga.various.mapdb;


import com.ccreanga.various.util.FormatUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.ehcache.sizeof.SizeOf;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class MapDbSizeBenchmark {

    public static final int MAP_SIZE = 20_000_000;


    public static void main(String[] args) throws IOException {



        SizeOf sizeOf = SizeOf.newInstance();
        ConcurrentMap<Long,Long> jdkMap = new ConcurrentHashMap<>(MAP_SIZE);
        for (int i = 0; i < Common.MAP_SIZE; i++) {
            jdkMap.put((long) i, (long)(Math.random()*Long.MAX_VALUE));
        }
        long deepSize = sizeOf.deepSizeOf(jdkMap);
        System.out.println(FormatUtil.readableSize(deepSize));

        String DB_BTREE_PATH = "/tmp/b-tree.db";
        String DB_HTREE_PATH = "/tmp/f-tree.db";
        Path dbPath = Common.newPath(DB_BTREE_PATH);


        DB db = DBMaker
            .fileDB(dbPath.toString())
            .fileMmapEnable()
            .make();
        ConcurrentMap<Long, Long> map = db
            .treeMap("map", Serializer.LONG, Serializer.LONG)
            .create();
        for (int i = 0; i < Common.MAP_SIZE; i++) {
            map.put((long) i, (long)(Math.random()*Long.MAX_VALUE));
        }
        db.commit();
        System.out.println(FormatUtil.readableSize(Common.size(DB_BTREE_PATH)));


        dbPath = Common.newPath(DB_HTREE_PATH);

        db = DBMaker
            .fileDB(dbPath.toString())
            .fileMmapEnable()
            .make();
        map = db
            .hashMap("map", Serializer.LONG, Serializer.LONG)
            .create();
        for (int i = 0; i < Common.MAP_SIZE; i++) {
            map.put((long) i, (long)(Math.random()*Long.MAX_VALUE));
        }
        db.commit();
        System.out.println(FormatUtil.readableSize(Common.size(DB_HTREE_PATH)));
    }

}
