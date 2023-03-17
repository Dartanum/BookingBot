package ru.dartanum.bookingbot.app.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_TICKET_SEARCHING;
import static ru.dartanum.bookingbot.app.constant.Variables.*;

@Component
public class ChooseBookingDateRangeAction implements MessageAction {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        long userId = message.getFrom().getId();
        var parts = message.getText().split("-");
        LocalDate from = LocalDate.parse(parts[0].trim(), DATE_TIME_FORMATTER);
        LocalDate to = null;
        if (parts.length == 2) {
            to = LocalDate.parse(parts[1].trim(), DATE_TIME_FORMATTER);
        }
        Storage.putVariable(userId, BOOKING_DATE_FROM, from);
        Storage.putVariable(userId, BOOKING_DATE_TO, to);

        sendMessage.setText(MSG_TICKET_SEARCHING);
        return BotState.BOOKING_DATE_CHOSEN;
    }
}
