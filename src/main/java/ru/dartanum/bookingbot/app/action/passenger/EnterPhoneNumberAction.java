package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_ENTER_EMAIL;

@Component
@RequiredArgsConstructor
public class EnterPhoneNumberAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    @Transactional
    public BotState execute(SendMessage sendMessage, Message message) {
        var storage = userStorageRepository.getById(message.getFrom().getId());
        var phoneNumber = message.getText().trim();

        storage.getNewPassenger().setPhoneNumber(phoneNumber);
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_ENTER_EMAIL);

        return BotState.CREATING_PASSENGER_enterEmail;
    }
}
