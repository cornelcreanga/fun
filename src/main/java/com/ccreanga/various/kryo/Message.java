package com.ccreanga.various.kryo;

public class Message {

    private long id;
    private long matchId;
    private byte[] message;
    private long timestamp;

    public Message(long id, long matchId, byte[] message, long timestamp) {
        this.id = id;
        this.matchId = matchId;
        this.message = message;
        this.timestamp = timestamp;
    }
}
