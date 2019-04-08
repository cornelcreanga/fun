package com.ccreanga.various.kryo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Message {

    private long id;
    private long matchId;
    private byte[] message;
    private long timestamp;

    private Message() {
    }

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

        if (message!=null){
            out.write(message.length);
            out.write(message);
        }else{
            out.write(0);
        }


        out.write((int)(timestamp >> 32));
        out.write((int)timestamp);
    }

    public static Message readExternal(InputStream in) throws IOException {
        Message m = new Message();
        int a,b;
        a = in.read();
        b = in.read();
        m.id = (long)a << 32 | b & 0xFFFFFFFFL;
        a = in.read();
        b = in.read();
        m.matchId = (long)a << 32 | b & 0xFFFFFFFFL;
        a = in.read();
        if (a!=0) {
            m.message = new byte[a];
            in.read(m.message);
        }
        a = in.read();
        b = in.read();
        m.timestamp = (long)a << 32 | b & 0xFFFFFFFFL;
        return m;
    }
}
