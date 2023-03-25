package ru.dartanum.bookingbot.app.action;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_INCORRECT_DATA;

@Component
@RequiredArgsConstructor
public class IncorrectEnteredDataAction implements MessageAction {
    public final UserStorageRepository userStorageRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        sendMessage.setText(MSG_INCORRECT_DATA);
        return userStorageRepository.getBotState(message.getFrom().getId());
    }
}
