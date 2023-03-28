package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.dartanum.bookingbot.app.BookingBot;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.KeyboardFactory;
import ru.dartanum.bookingbot.app.MessageFactory;
import ru.dartanum.bookingbot.app.action.CallbackQueryAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.user.TelegramPassenger;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StartBookTicketAction implements CallbackQueryAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;

    @Override
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
        var flightId = UUID.fromString(CallbackQueryAction.getCallbackParams(callbackQuery).get(0));
        var storage = userStorageRepository.getById(callbackQuery.getFrom().getId());
        var passengers = storage.getPassengers();

        storage.getNewTicket().setFlightId(flightId);
        userStorageRepository.save(storage);

        sendMessage.enableMarkdownV2(true);
        sendMessage.setText(MessageFactory.mdPassengerList(passengers));
        sendMessage.setReplyMarkup(KeyboardFactory.inlineListOfVariantsWithId(passengers.stream().map(TelegramPassenger::getId).toList()));
        bookingBot.deleteInlineKeyboard(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId());

        return BotState.BOOKING_flight_chosen;
    }
}
