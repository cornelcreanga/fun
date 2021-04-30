package com.ccreanga.various.fun;

import java.io.StringWriter;
import java.io.Writer;

public class Permutation {

    public static void permutation(String str, Writer out) throws Exception {
        permutation("", str, out);
    }

    private static void permutation(String prefix, String str, Writer out) throws Exception {
        int n = str.length();
        if (n == 0) {
            out.write(prefix, 0, prefix.length());
            out.write("\n");
        } else {
            for (int i = 0; i < n; i++)
                permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), out);
        }
    }


    public static void main(String[] args) throws Exception{
        StringWriter writer = new StringWriter();
        permutation("abcd", writer);
        writer.close();
        System.out.println(writer.getBuffer());
    }

}
