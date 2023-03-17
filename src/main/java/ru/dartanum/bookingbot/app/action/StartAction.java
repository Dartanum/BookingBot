package ru.dartanum.bookingbot.app.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.KeyboardFactory;
import ru.dartanum.bookingbot.app.constant.BotReplyConstants;

@Component
public class StartAction implements MessageAction {
    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        sendMessage.setText(BotReplyConstants.MSG_START);
        sendMessage.setReplyMarkup(KeyboardFactory.replyMenuKeyboard());

        return BotState.MENU;
    }
}
