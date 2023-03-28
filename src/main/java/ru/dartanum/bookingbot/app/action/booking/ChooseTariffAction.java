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
import ru.dartanum.bookingbot.app.api.repository.SeatPriceRepository;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.Seat;
import ru.dartanum.bookingbot.domain.SeatClass;
import ru.dartanum.bookingbot.domain.price.Currency;
import ru.dartanum.bookingbot.domain.price.SeatPrice;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChooseTariffAction implements CallbackQueryAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;
    private final FlightRepository flightRepository;
    private final SeatPriceRepository seatPriceRepository;

    @Override
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
        var tariffId = UUID.fromString(CallbackQueryAction.getCallbackParams(callbackQuery).get(0));
        var storage = userStorageRepository.getById(callbackQuery.getFrom().getId());
        var flight = flightRepository.findById(storage.getNewTicket().getFlightId());
        var seats = flight.getAirplane().getModel().getSeats();

        Set<SeatClass> seatClasses = seats.stream().distinct().map(Seat::getSeatClass).collect(Collectors.toSet());
        Map<String, SeatPrice> seatPriceByClassName = seatClasses.stream().collect(Collectors.toMap(
                SeatClass::getName,
                seatClass -> seatPriceRepository.findByClassCodeAndTimetableId(seatClass.getCode(), flight.getTimetable().getId())));

        storage.getNewTicket().setTariffId(tariffId);
        storage.getNewTicket().setCurrencySymbol(seatPriceByClassName.values().stream().findFirst().orElse(new SeatPrice().setCurrency(new Currency())).getCurrency().getSymbol());
        userStorageRepository.save(storage);

        sendMessage.setText(MessageFactory.seatClasses(seatPriceByClassName));
        sendMessage.setReplyMarkup(KeyboardFactory.inlineSeatClassList(List.copyOf(seatClasses)));
        bookingBot.deleteMessage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId());

        return BotState.BOOKING_tariff_chosen;
    }
}
