package com.ccreanga.various.kryo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public void writeExternal(OutputStream out) throws IOException {
        out.write((int)(id >> 32));
        out.write((int)id);

        out.write((int)(matchId >> 32));
        out.write((int)matchId);

        out.write(message.length);
        out.write(message);

        out.write((int)(timestamp >> 32));
        out.write((int)timestamp);
    }

    public void readExternal(InputStream in) throws IOException {
        int a,b;
        a = in.read();
        b = in.read();
        id = (long)a << 32 | b & 0xFFFFFFFFL;
        a = in.read();
        b = in.read();
        matchId = (long)a << 32 | b & 0xFFFFFFFFL;
        a = in.read();
        message = new byte[a];
        in.read(message);
        a = in.read();
        b = in.read();
        timestamp = (long)a << 32 | b & 0xFFFFFFFFL;
    }

    public static byte[] longToBytes(long l) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return result;
    }

    public static long bytesToLong(byte[] b) {
        long result = 0;
        for (int i = 0; i < 8; i++) {
            result <<= 8;
            result |= (b[i] & 0xFF);
        }
        return result;
    }
}
