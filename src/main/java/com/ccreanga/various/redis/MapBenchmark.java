package com.ccreanga.various.redis;

import redis.clients.jedis.Jedis;


public class MapBenchmark {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("172.17.0.2");
        //adding a new key
        jedis.set("key", "value");
        //getting the key value
        System.out.println(jedis.get("key"));
    }

}
