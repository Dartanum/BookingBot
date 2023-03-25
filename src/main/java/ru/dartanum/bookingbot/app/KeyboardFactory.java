package ru.dartanum.bookingbot.app;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

import static ru.dartanum.bookingbot.app.constant.CallbackActionConstants.*;
import static ru.dartanum.bookingbot.app.constant.MessageActionConstants.*;

public class KeyboardFactory {
    public static ReplyKeyboardMarkup replyMenuKeyboard() {
        KeyboardRow row1 = new KeyboardRow() {{
            add(new KeyboardButton(ACT_GET_TICKETS));
            add(new KeyboardButton(ACT_GET_PASSENGERS));
        }};
        KeyboardRow row2 = new KeyboardRow() {{
            add(new KeyboardButton(ACT_START_BOOKING));
            add(new KeyboardButton(ACT_GO_TO_MENU));
        }};

        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .keyboard(List.of(row1, row2))
                .build();
    }

    public static ReplyKeyboardMarkup replyAddPassengerKeyboard() {
        KeyboardRow row = new KeyboardRow(List.of()) {{
            add(new KeyboardButton(ACT_ADD_PASSENGER));
            add(new KeyboardButton(ACT_GO_TO_MENU));
        }};

        return ReplyKeyboardMarkup.builder()
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .keyboard(List.of(row))
                .build();
    }

    public static InlineKeyboardMarkup inlinePassengerOps(UUID passengerId) {
        InlineKeyboardButton editBtn = InlineKeyboardButton.builder()
                .text("Изменить").callbackData(ACT_EDIT_PASSENGER + passengerId).build();
        InlineKeyboardButton deleteBtn = InlineKeyboardButton.builder()
                .text("Удалить").callbackData(ACT_DELETE_PASSENGER + passengerId).build();

        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(List.of(editBtn, deleteBtn)))
                .build();
    }

    public static InlineKeyboardMarkup inlineEditPassengerOps(UUID passengerId) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(new ArrayList<>() {{
            add(constructInlineButton("Имя", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[0]));
            add(constructInlineButton("Фамилия", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[1]));
            add(constructInlineButton("Отчество", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[2]));
        }});
        rows.add(new ArrayList<>() {{
            add(constructInlineButton("Телефон", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[3]));
            add(constructInlineButton("Почта", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[4]));
            add(constructInlineButton("Дата рождения", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[5]));
        }});
        rows.add(new ArrayList<>() {{
            add(constructInlineButton("Пол", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[6]));
            add(constructInlineButton("Гражданство", ACT_EDIT_PASSENGER_CHOOSE_FIELD, passengerId, EDIT_PASSENGER_FIELDS[7]));
        }});

        return InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();
    }

    private static InlineKeyboardButton constructInlineButton(String text, String action, Object... params) {
        String[] stringParams = Arrays.stream(params)
                .filter(Objects::nonNull)
                .map(Object::toString)
                .toArray(String[]::new);

        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(action + String.join(PARAM_DELIMITER, stringParams))
                .build();
    }
}
