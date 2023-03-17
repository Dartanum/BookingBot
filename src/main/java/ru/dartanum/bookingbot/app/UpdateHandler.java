package ru.dartanum.bookingbot.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static ru.dartanum.bookingbot.app.constant.ActionConstants.ACT_GO_TO_MENU;

@Component
@RequiredArgsConstructor
public class UpdateHandler {
    public void handle(Update update, SendMessage sendMessage) {
        if (update.hasCallbackQuery()) {
            var callback = update.getCallbackQuery();
            handleCallbackQuery(callback, sendMessage);
        } else if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                handleMessage(message, sendMessage);
            }
        }
    }

    private void handleMessage(Message message, SendMessage sendMessage) {
        long userId = message.getFrom().getId();
        if (isGoToMenuAction(message)) {
            Storage.updateBotState(userId, BotState.MENU);
        }

        BotState lastState = Storage.getBotStateByUserId(userId);
        BotState newState = lastState.nextState(sendMessage, message);
        Storage.updateBotState(userId, newState);
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery, SendMessage sendMessage) {
        long userId = callbackQuery.getFrom().getId();
        BotState lastState = Storage.getBotStateByUserId(userId);
        BotState newState = lastState.nextState(sendMessage, callbackQuery);
        Storage.updateBotState(userId, newState);
    }

    private boolean isGoToMenuAction(Message message) {
        return message.getText().equals(ACT_GO_TO_MENU);
    }
}
