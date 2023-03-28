package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
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
import static ru.dartanum.bookingbot.app.constant.Constants.FLIGHT_DATE_TIME_FORMATTER;

@Component
@RequiredArgsConstructor
public class ChooseBookingDateRangeAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    @Transactional
    public BotState execute(SendMessage sendMessage, Message message) {
        long userId = message.getFrom().getId();
        var parts = message.getText().split("-");
        var storage = userStorageRepository.getById(userId);

        LocalDate from = LocalDate.parse(parts[0].trim(), DATE_TIME_FORMATTER);
        LocalDate to = null;
        if (parts.length == 2) {
            to = LocalDate.parse(parts[1].trim(), DATE_TIME_FORMATTER);
        }

        if (to != null && (from.isAfter(to) || LocalDate.now().isAfter(from))) {
            sendMessage.setText(MSG_INVALID_DATE_RANGE);

            return BotState.BOOKING_start;
        }

        storage.setBookingDateRange(Pair.of(from, to));
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_CHOSE_SOURCE_AIRPORTS);

        return BotState.BOOKING_dates_chosen;
    }
}
