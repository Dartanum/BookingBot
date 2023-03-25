package ru.dartanum.bookingbot.app;

import ru.dartanum.bookingbot.domain.user.TelegramPassenger;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static ru.dartanum.bookingbot.util.StringUtils.createFullName;
import static ru.dartanum.bookingbot.util.StringUtils.escape;

public class MessageFactory {

    public static String passengerCard(TelegramPassenger passenger) {
        var builder = new StringBuilder("*ФИО*: " + createFullName(passenger.getSurname(), passenger.getName(), passenger.getFatherName()));
        builder.append(isNotBlank(passenger.getSex()) ? "\n*Пол*: " + passenger.getSex() : "");
        builder.append(isNotBlank(passenger.getPhoneNumber()) ? "\n*Телефон*: " + escape(passenger.getPhoneNumber()) : "");
        builder.append(isNotBlank(passenger.getEmail()) ? "\n*Почта*: " + escape(passenger.getEmail()) : "");
        builder.append(!passenger.getCitizenship().isEmpty() ? "\n*Гражданство*: " + passenger.getCitizenship() : "");

        return builder.toString();
    }
}
