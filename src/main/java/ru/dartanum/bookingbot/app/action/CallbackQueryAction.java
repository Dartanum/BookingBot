package ru.dartanum.bookingbot.app.action;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.dartanum.bookingbot.app.BotState;

import java.util.Arrays;
import java.util.List;

public interface CallbackQueryAction extends Action {
    BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery);

    static List<String> getCallbackParams(CallbackQuery query) {
        return Arrays.stream(query.getData().split(":")[1].split(",")).toList();
    }
}
