package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.CallbackQueryAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

import java.util.UUID;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_EDIT_PASSENGER_ENTER;

@Component
@RequiredArgsConstructor
public class EditPassengerChooseFieldAction implements CallbackQueryAction {
    private final UserStorageRepository userStorageRepository;

    @Override
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
        var userId = callbackQuery.getFrom().getId();
        var params = CallbackQueryAction.getCallbackParams(callbackQuery);
        var passengerId = UUID.fromString(params.get(0));
        var field = params.get(1);

        var storage = userStorageRepository.getById(userId);
        storage.setEditingPassengerField(Pair.of(passengerId, field));
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_EDIT_PASSENGER_ENTER);

        return BotState.EDITING_PASSENGER_enter_field;
    }
}
