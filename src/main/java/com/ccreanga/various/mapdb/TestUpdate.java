package com.ccreanga.various.mapdb;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;

public class TestUpdate {

    private static ConcurrentMap<String, String> map;
    private static DB db;

    public static void main(String[] args) throws IOException {

        Path dbPath = Paths.get("/tmp/b-tree-test.db");
        if (dbPath.toFile().exists()) {
            Files.delete(dbPath);
        }
        db = DBMaker
                .fileDB("/tmp/b-tree-test.db")
                .fileMmapEnable()
                .make();
        map = db
                .treeMap("map", Serializer.STRING, Serializer.STRING)
                .create();
        for (int i = 0; i < 10_000_000; i++) {
            map.put("testupdate"+i, "item" + i);
        }
        db.commit();
        System.out.println(Files.size(dbPath));
        for (int i = 0; i < 10_000_000; i++) {
            map.put("testupdate"+i, "item1" + i);
        }
        db.commit();
        System.out.println(Files.size(dbPath));
        db.getStore().compact();
        db.commit();
        System.out.println(Files.size(dbPath));
    }
}
