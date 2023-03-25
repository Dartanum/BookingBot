package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_ENTER_CITIZENSHIP;

@Component
@RequiredArgsConstructor
public class EnterEmailAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        var storage = userStorageRepository.getById(message.getFrom().getId());
        var email = message.getText().trim();

        storage.getNewPassenger().setEmail(email);
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_ENTER_CITIZENSHIP);

        return BotState.CREATING_PASSENGER_enterCitizenship;
    }
}
