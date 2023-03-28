package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import java.time.LocalDate;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_DATE_AFTER_NOW;
import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_ENTER_PHONE_NUMBER;
import static ru.dartanum.bookingbot.app.constant.Constants.DATE_TIME_FORMATTER;
import static ru.dartanum.bookingbot.app.constant.Constants.FLIGHT_DATE_TIME_FORMATTER;

@Component
@RequiredArgsConstructor
public class EnterBirthDateAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        var storage = userStorageRepository.getById(message.getFrom().getId());
        LocalDate birthDate = LocalDate.parse(message.getText().trim(), DATE_TIME_FORMATTER);

        if (birthDate.isAfter(LocalDate.now().minusDays(1))) {
            sendMessage.setText(MSG_DATE_AFTER_NOW);

            return BotState.CREATING_PASSENGER_enterBirthDate;
        } else {
            storage.getNewPassenger().setBirthDate(birthDate);
            userStorageRepository.save(storage);
            sendMessage.setText(MSG_ENTER_PHONE_NUMBER);

            return BotState.CREATING_PASSENGER_enterPhoneNumber;
        }
    }
}
