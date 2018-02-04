package com.ccreanga.various.airport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String time() {
        LocalDateTime date = LocalDateTime.now();
        String text = date.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return text;
    }
}
