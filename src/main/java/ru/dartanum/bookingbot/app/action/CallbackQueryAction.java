package ru.dartanum.bookingbot.app.action;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;

public interface CallbackQueryAction extends Action {
    BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery);
}
