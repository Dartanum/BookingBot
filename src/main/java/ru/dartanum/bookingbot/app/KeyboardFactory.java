package ru.dartanum.bookingbot.app;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static ru.dartanum.bookingbot.app.constant.Action.*;

public class KeyboardFactory {
    public static ReplyKeyboard menuButtons() {
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        rows.add(new ArrayList<>(){{
            add(InlineKeyboardButton.builder().text("Мои билеты").callbackData(GET_TICKETS).build());
            add(InlineKeyboardButton.builder().text("Пассажиры").callbackData(GET_PASSENGERS).build());
        }});
        rows.add(new ArrayList<>(){{
            add(InlineKeyboardButton.builder().text("Бронирование").callbackData(START_BOOKING).build());
        }});
        keyboard.setKeyboard(rows);

        return keyboard;
    }
}
