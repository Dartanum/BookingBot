package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_ENTER_FULL_NAME;

@Component
@RequiredArgsConstructor
public class CreatePassengerAction implements MessageAction {
    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        sendMessage.setText(MSG_ENTER_FULL_NAME);
        return BotState.CREATING_PASSENGER_enterFullName;
    }
}
