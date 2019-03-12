package com.ccreanga.various.romannumber;

import java.util.LinkedHashMap;
import java.util.Set;

public class Convert {

    static private LinkedHashMap<Integer,String> symbols = new LinkedHashMap<>(16);
    static{
        symbols.put(1000,"M");
        symbols.put(900,"CM");
        symbols.put(500,"D");
        symbols.put(400,"CD");
        symbols.put(100,"C");
        symbols.put(90,"XC");
        symbols.put(50,"L");
        symbols.put(40,"XL");
        symbols.put(10,"X");
        symbols.put(9,"IX");
        symbols.put(8,"VIII");
        symbols.put(7,"VII");
        symbols.put(6,"VI");
        symbols.put(5,"V");
        symbols.put(4,"IV");
        symbols.put(3,"III");
        symbols.put(2,"II");
        symbols.put(1,"I");
    }

    public static String toRoman(int no){
        StringBuilder sb = new StringBuilder(16);
        String cached = symbols.get(no);
        if (cached!=null)
            return cached;
        Set<Integer> keys = symbols.keySet();
        for (Integer k : keys) {
            if (no==k){
                sb.append(symbols.get(k));
                break;
            }else if (no>k) {
                String v = symbols.get(k);
                int digit = no/k;
                sb.append(repeatChars(v,digit));
                no = no - digit*k;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 1; i < 3999; i++) {
            System.out.println(toRoman(i));
        }
    }


    private static String repeatChars(String c, int count) {
        StringBuilder stringBuilder = new StringBuilder(c.length()*count);
        for(int i=0;i<count;i++)
            stringBuilder.append(c);
        return stringBuilder.toString();
    }

}
