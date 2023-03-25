package ru.dartanum.bookingbot.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import static org.apache.commons.lang3.StringUtils.*;
import static ru.dartanum.bookingbot.app.BotState.CALLBACK_QUERY;
import static ru.dartanum.bookingbot.app.constant.MessageActionConstants.ACT_GO_TO_MENU;
import static ru.dartanum.bookingbot.app.constant.MessageActionConstants.ACT_START;

@Component
@RequiredArgsConstructor
public class UpdateHandler {
    private final UserStorageRepository userStorageRepository;

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
            userStorageRepository.setBotState(userId, BotState.MENU);
            handleMenuState(sendMessage, userId);
        } else {
            BotState lastState = userStorageRepository.getBotState(userId);
            BotState newState = lastState.nextState(sendMessage, message);
            if (newState == BotState.MENU) {
                handleMenuState(sendMessage, userId);
            }
            userStorageRepository.setBotState(userId, newState);
        }
    }

    private void handleCallbackQuery(CallbackQuery callbackQuery, SendMessage sendMessage) {
        long userId = callbackQuery.getFrom().getId();
        var nextState = CALLBACK_QUERY.nextState(sendMessage, callbackQuery);

        if (nextState == CALLBACK_QUERY) { // is not global callback
            BotState lastState = userStorageRepository.getBotState(userId);
            nextState = lastState.nextState(sendMessage, callbackQuery);
        }

        if (nextState == BotState.MENU) {
            handleMenuState(sendMessage, userId);
        }

        userStorageRepository.setBotState(userId, nextState);
    }

    private boolean isGoToMenuAction(Message message) {
        return message.getText().equals(ACT_GO_TO_MENU) || message.getText().equals(ACT_START);
    }

    private void handleMenuState(SendMessage sendMessage, Long userId) {
        userStorageRepository.reset(userId);
        if (isBlank(sendMessage.getText())) {
            sendMessage.setText("Выберите действие");
        }
        sendMessage.setReplyMarkup(KeyboardFactory.replyMenuKeyboard());
    }
}
