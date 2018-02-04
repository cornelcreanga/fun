package com.ccreanga.various;


import java.util.HashMap;

public class WellFormedText {


    public boolean isWellFormed(String s) {
        int deschise = 0;
        HashMap<Character, Integer> paranteze = new HashMap<>();
        paranteze.put('{', 0);
        paranteze.put('(', 0);
        paranteze.put('[', 0);
        boolean intercalate = false;

        for (int i = 0; i < s.length() && !intercalate; i++) {
            if (paranteze.get('{') < 0 || paranteze.get('[') < 0 || paranteze.get('(') < 0) {
                intercalate = true;
            }
            switch (s.charAt(i)) {
                case '{': {
                    deschise++;
                    paranteze.put('{', paranteze.get('{') + 1);
                    break;
                }
                case '[': {
                    deschise++;
                    paranteze.put('[', paranteze.get('[') + 1);
                    break;
                }
                case '(': {
                    deschise++;
                    paranteze.put('(', paranteze.get('(') + 1);
                    break;
                }
                case '}': {
                    deschise--;
                    paranteze.put('{', paranteze.get('{') - 1);
                    break;
                }
                case ']': {
                    deschise--;
                    paranteze.put('[', paranteze.get('[') - 1);
                    break;
                }
                case ')': {
                    deschise--;
                    paranteze.put('(', paranteze.get('(') - 1);
                    break;
                }

            }
        }
        return deschise == 0 && !intercalate;
    }

    public boolean isWellFormed2(String s) {
        int[] signs = new int[3];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '{':
                    signs[0]++;
                    break;
                case '}':
                    signs[0]--;
                    break;
                case '[':
                    signs[1]++;
                    break;
                case ']':
                    signs[1]--;
                    break;
                case '(':
                    signs[2]++;
                    break;
                case ')':
                    signs[2]--;
                    break;

            }
            //early break - not sure if it really helps
            for (int sign : signs) {
                if (sign < 0)
                    return false;
            }

        }
        for (int sign : signs) {
            if (sign != 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        WellFormedText wellFormedText = new WellFormedText();
        if (args.length == 0) {
            System.err.println("no argument provided");
            System.exit(1);
        }
        System.out.println(wellFormedText.isWellFormed(args[0]));
    }
}
