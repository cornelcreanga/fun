package com.ccreanga.various.strings;

public class Palindrome {

    public static boolean isPalindrome(String s){
        int i = 0, j=s.length()-1;
        char[] c = s.toCharArray();
        while(i<j){
            while((c[i]=='*') && (i<j))
                i++;
            while((c[j]=='*') && (i<j))
                j--;

            if (c[i]!=c[j])
                return false;
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("ab*a"));
    }


}
