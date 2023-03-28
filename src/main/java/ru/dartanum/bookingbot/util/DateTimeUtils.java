package ru.dartanum.bookingbot.util;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

public class DateTimeUtils {
    private static final long MINUTES_PER_HOUR = 60;
    private static final long HOURS_PER_DAY = 24;

    private DateTimeUtils() {}

    public static String formattedDurationBetween(LocalDateTime from, LocalDateTime to) {
        var durationInMinutes = MINUTES.between(from, to);
        var durationInHours = durationInMinutes / MINUTES_PER_HOUR;
        var days = durationInHours / HOURS_PER_DAY;
        var hours = durationInHours - days * HOURS_PER_DAY;
        var minutes = durationInMinutes - durationInHours * MINUTES_PER_HOUR;

        String result = "";
        result += days != 0 ? days + "д " : "";
        result += hours != 0 ? hours + "ч " : "";
        result += minutes != 0 ? minutes + "м" : "";

        return result.trim();
    }
}
