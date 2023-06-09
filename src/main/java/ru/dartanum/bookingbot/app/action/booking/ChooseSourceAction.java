package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.dartanum.bookingbot.app.BookingBot;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.action.CallbackQueryAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.Place;

import java.util.Arrays;

import static ru.dartanum.bookingbot.app.MessageFactory.placeClassByName;
import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_CHOOSE_DESTINATION_AIRPORT;

@Component
@RequiredArgsConstructor
public class ChooseSourceAction implements CallbackQueryAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;

    @Override
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
        var chosenVariant = CallbackQueryAction.getCallbackParams(callbackQuery).get(0);
        var text = callbackQuery.getMessage().getText();
        var userId = callbackQuery.getFrom().getId();
        var storage = userStorageRepository.getById(userId);

        var sourcePlace = PlaceCreator.getPlaceByVariantNumber(text, chosenVariant);
        storage.setBookingFromTo(Pair.of(sourcePlace, null));
        userStorageRepository.save(storage);
        sendMessage.setText(MSG_CHOOSE_DESTINATION_AIRPORT);
        bookingBot.deleteMessage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId());

        return BotState.BOOKING_source_place_chosen;
    }
}
