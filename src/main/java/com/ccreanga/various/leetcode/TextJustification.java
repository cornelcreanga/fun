package com.ccreanga.various.leetcode;


import java.util.ArrayList;
import java.util.List;

public class TextJustification {
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<List<String>> lines = new ArrayList<>();
        List<String> justifiedLines = new ArrayList<>();
        List<String> currentLine = new ArrayList<>();
        int l = 0;
        int i = 0;
        while (i < words.length) {
            String word = words[i];
            int max = currentLine.isEmpty() ? maxWidth : maxWidth - 1;
            if ((l + word.length()) <= max) {
                l += word.length() + (currentLine.isEmpty() ? 0 : 1);
                currentLine.add(word);
            } else {
                lines.add(currentLine);
                currentLine = new ArrayList<>();
                currentLine.add(word);
                l = word.length();
            }
            i++;
        }
        if (!currentLine.isEmpty())
            lines.add(currentLine);

        for (i = 0; i < lines.size(); i++) {
            justifiedLines.add(alignWords(lines.get(i), maxWidth, i==(lines.size()-1)));
        }
        return justifiedLines;
    }

    private static String alignWords(List<String> currentLine, int maxWidth, boolean lastLine) {
        if (currentLine.size() == 1) {
            String first = currentLine.get(0);
            return first + " ".repeat(maxWidth - first.length());
        }
        int letterLenght = currentLine.stream().map(String::length).reduce(0, Integer::sum);
        int spaces = (maxWidth - letterLenght);



        int spacesBetween = spaces / (currentLine.size() - 1);
        int extrasSpaces = spaces - (spacesBetween * (currentLine.size() - 1));

        if(lastLine){
           spacesBetween = 1;
           extrasSpaces = 0;
        }

        StringBuilder sb = new StringBuilder(currentLine.size() * 12);
        for (int i = 0; i < currentLine.size(); i++) {
            String word = currentLine.get(i);
            sb.append(word);
            if (i < (currentLine.size() - 1)) {
                sb.append(" ".repeat(spacesBetween));
                if (extrasSpaces > 0){
                    sb.append(" ");
                    extrasSpaces--;
                }
            }
        }
        if (lastLine)
            sb.append(" ".repeat(spaces - (currentLine.size() - 1)));
        return sb.toString();
    }

}
