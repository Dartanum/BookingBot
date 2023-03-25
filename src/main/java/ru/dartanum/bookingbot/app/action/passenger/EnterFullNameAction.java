package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.user.TelegramPassenger;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_ENTER_SEX;

@Component
@RequiredArgsConstructor
public class EnterFullNameAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    @Transactional
    public BotState execute(SendMessage sendMessage, Message message) {
        Long userId = message.getFrom().getId();
        var storage = userStorageRepository.getById(userId);
        var fullNameParts = message.getText().trim().split(" ");

        storage.setNewPassenger(fillFullName(storage.getNewPassenger(), fullNameParts));
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_ENTER_SEX);

        return BotState.CREATING_PASSENGER_enterSex;
    }

    private TelegramPassenger fillFullName(TelegramPassenger passenger, String[] fullNameParts) {
        passenger.setSurname(fullNameParts[0]);
        passenger.setName(fullNameParts[1]);
        passenger.setFatherName(fullNameParts.length == 3 ? fullNameParts[2] : null);

        return passenger;
    }
}
