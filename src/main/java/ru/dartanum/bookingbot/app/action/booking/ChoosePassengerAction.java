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
import ru.dartanum.bookingbot.app.api.repository.FlightRepository;
import ru.dartanum.bookingbot.app.api.repository.TariffPriceRepository;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.Tariff;
import ru.dartanum.bookingbot.domain.price.TariffPrice;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChoosePassengerAction implements CallbackQueryAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;
    private final TariffPriceRepository tariffPriceRepository;
    private final FlightRepository flightRepository;

    @Override
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
       var passengerId = UUID.fromString(CallbackQueryAction.getCallbackParams(callbackQuery).get(0));
       var storage = userStorageRepository.getById(callbackQuery.getFrom().getId());
       var passenger = storage.getPassengers().stream().filter(psngr -> psngr.getId().equals(passengerId)).findFirst().orElse(null);
       var flight = flightRepository.findById(storage.getNewTicket().getFlightId());
       var tariffs = flight.getAirplane().getAirline().getTariffs();
       Map<Tariff, TariffPrice> tariffWithPrice = tariffPriceRepository.findAllByTariffs(tariffs);

       storage.getNewTicket().setPassenger(passenger);
       userStorageRepository.save(storage);
       sendMessage.enableMarkdownV2(true);
       sendMessage.setText(MessageFactory.mdTariffList(tariffWithPrice));
       sendMessage.setReplyMarkup(KeyboardFactory.inlineListOfVariantsWithId(tariffs.stream().map(Tariff::getId).toList()));
       bookingBot.deleteMessage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId());

       return BotState.BOOKING_passenger_chosen;
    }
}
