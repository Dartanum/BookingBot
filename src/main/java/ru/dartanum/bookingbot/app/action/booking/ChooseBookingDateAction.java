package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import java.time.LocalDate;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_CHOSE_SOURCE_AIRPORTS;
import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_INVALID_DATE_RANGE;
import static ru.dartanum.bookingbot.app.constant.Constants.DATE_TIME_FORMATTER;

@Component
@RequiredArgsConstructor
public class ChooseBookingDateAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    @Transactional
    public BotState execute(SendMessage sendMessage, Message message) {
        long userId = message.getFrom().getId();
        var storage = userStorageRepository.getById(userId);

        LocalDate date = LocalDate.parse(message.getText().trim(), DATE_TIME_FORMATTER);

        if (LocalDate.now().isAfter(date)) {
            sendMessage.setText(MSG_INVALID_DATE_RANGE);

            return BotState.BOOKING_start;
        }

        storage.setBookingDate(date);
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_CHOSE_SOURCE_AIRPORTS);

        return BotState.BOOKING_dates_chosen;
    }
}
