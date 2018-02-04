package com.ccreanga.various.strings;

import java.util.Arrays;


/**
 * Assume a string array composed from positive numbers eq '12','124','43','125' . Obtain the largest number by
 * concatenating the array numbers eg 4312512412
 */
public class MaximumNumber {

    public static String computeMaximumNumber(String[] s) {
        StringBuilder result = new StringBuilder(s.length * 6);
        for (String value : s) {
            try {
                int val = Integer.parseInt(value);
                if (val < 0)
                    throw new IllegalArgumentException(value + " is not a positive integer");
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(value + " is not a positive integer");
            }

        }
        Arrays.sort(s, (s1, s2) -> {
            int s1Len = s1.length(), s2Len = s2.length();
            int len = Math.min(s1Len, s2Len);
            for (int i = 0; i < len; i++) {
                if (s1.charAt(i) != s2.charAt(i))
                    return s2.charAt(i) - s1.charAt(i);
            }
            if (s1Len > s2Len)
                return s2.charAt(0) - s1.charAt(s1Len - 1);
            else if (s1Len < s2Len)
                return s2.charAt(s2Len - 1) - s1.charAt(0);
            else//both are equal
                return 0;
        });
        for (String value : s) {
            result.append(value);
        }
        return result.toString();

    }

}
