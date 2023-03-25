package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.dartanum.bookingbot.app.BookingBot;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.CallbackQueryAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import java.util.UUID;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_NONEXISTENT_PASSENGER;

@Component
@RequiredArgsConstructor
public class DeletePassengerAction implements CallbackQueryAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;

    @Override
    @Transactional
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
        var userId = callbackQuery.getFrom().getId();
        var passengerId = UUID.fromString(CallbackQueryAction.getCallbackParams(callbackQuery).get(0));
        var storage = userStorageRepository.getById(userId);
        var passenger = storage.getPassengerById(passengerId);

        if (passenger != null) {
            storage.removePassengerById(passengerId);
            userStorageRepository.save(storage);
        } else {
            sendMessage.setText(MSG_NONEXISTENT_PASSENGER);
        }

        bookingBot.send(DeleteMessage.builder()
                .messageId(callbackQuery.getMessage().getMessageId())
                .chatId(callbackQuery.getMessage().getChatId())
                .build());

        return BotState.PASSENGER_LIST;
    }
}
