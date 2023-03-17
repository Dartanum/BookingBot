package ru.dartanum.bookingbot.app.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_INVALID_BOOKING_DATE_FORMAT;

@Component
public class ReplyToWrongBookingDateRange implements MessageAction {
    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        sendMessage.setText(MSG_INVALID_BOOKING_DATE_FORMAT);
        return BotState.START_BOOKING;
    }
}
