package com.ccreanga.various.fun;

import java.util.HashMap;

public class Decode {

    private static final HashMap<String, Character> mapping = new HashMap<>();

    static {
        int c = 1;
        for (int i = 'A'; i < 'Z'; i++) {
            mapping.put( "" + c,(char) i);
            c++;
        }
    }


    public static void doMapping(String word, String prefix, int position){
        if (position == word.length()){
            System.out.println(word);
        }
        if (mapping.containsKey(prefix)){
            doMapping(word,prefix + word.charAt(position +1 ), position );
        }
    }

    public static void main(String[] args) {
        String s = "226";
        doMapping(s,"2", 0);
        //"BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
        //2,26 B,B, F
    }
}
