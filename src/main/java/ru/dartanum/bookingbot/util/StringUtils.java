package ru.dartanum.bookingbot.util;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

public class StringUtils {
    private static final String[] ESCAPED_CHARACTERS = {"+", ".", "_", "!"};

    private StringUtils() {}

    public static String createFullName(String surname, String name, String fatherName) {
        return String.join(" ", surname, name, Objects.requireNonNullElse(fatherName, "")).trim();
    }

    public static String escape(String str) {
        String formattedStr = str;
        for (String character : ESCAPED_CHARACTERS) {
            formattedStr = formattedStr.replaceAll(format("\\%s", character), "\\\\" + character);
        }

        return formattedStr;
    }

    public static List<String> toWordListSeparatedByComma(String text) {
        return List.of(text.trim().split(",\\s*"));
    }
}
