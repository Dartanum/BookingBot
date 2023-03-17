package ru.dartanum.bookingbot.app.action;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;

@Component
public class GoToMenuAction implements MessageAction {
    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        return BotState.MENU;
    }
}
