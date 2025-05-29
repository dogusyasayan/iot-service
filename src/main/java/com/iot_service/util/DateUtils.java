package com.iot_service.util;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    private DateUtils() {

    }

    public static String epochMillisToString(long epochMillis) {
        return FORMATTER.format(Instant.ofEpochMilli(epochMillis));
    }
}