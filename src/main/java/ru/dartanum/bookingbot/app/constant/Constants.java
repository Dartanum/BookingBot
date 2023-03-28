package ru.dartanum.bookingbot.app.constant;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface Constants {
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    DateTimeFormatter FLIGHT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm, d MMM", Locale.forLanguageTag("RU"));
}
