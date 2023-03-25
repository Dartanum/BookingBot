package ru.dartanum.bookingbot.util;

import java.time.LocalDate;

public class RedisUtils {
    private RedisUtils() {}

    public static LocalDate convertLongToLocalDate(Long epochDay) {
        return epochDay == null ? null : LocalDate.ofEpochDay(epochDay);
    }

    public static Long convertLocalDateToLong(LocalDate date) {
        return date == null ? null : date.toEpochDay();
    }
}
