package com.ccreanga.various.airport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String time() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
