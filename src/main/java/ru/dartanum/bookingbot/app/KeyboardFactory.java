package ru.dartanum.bookingbot.app;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

import static ru.dartanum.bookingbot.app.constant.ActionConstants.*;

public class KeyboardFactory {
    public static ReplyKeyboard replyMenuKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow() {{
            add(new KeyboardButton(ACT_GET_TICKETS));
            add(new KeyboardButton(ACT_GET_PASSENGERS));
        }};
        KeyboardRow row2 = new KeyboardRow() {{
            add(new KeyboardButton(ACT_START_BOOKING));
            add(new KeyboardButton(ACT_GO_TO_MENU));
        }};
        keyboard.setKeyboard(new ArrayList<>() {{add(row1); add(row2);}});
        keyboard.setOneTimeKeyboard(true);

        return keyboard;
    }
}
