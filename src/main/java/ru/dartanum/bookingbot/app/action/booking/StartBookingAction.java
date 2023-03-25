package ru.dartanum.bookingbot.app.action.booking;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_ASK_DATE_RANGE;

@Component
public class StartBookingAction implements MessageAction {
    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        sendMessage.setText(MSG_ASK_DATE_RANGE);
        return BotState.BOOKING_start;
    }
}
