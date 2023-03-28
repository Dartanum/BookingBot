package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.MessageAction;

@Component
@RequiredArgsConstructor
public class EnterDestinationAction implements MessageAction {
    private final SearchPlaceByTextAbstractAction searchPlaceByTextAbstractAction;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        searchPlaceByTextAbstractAction.execute(sendMessage, message);

        return BotState.BOOKING_source_place_chosen;
    }
}
