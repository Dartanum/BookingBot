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
import ru.dartanum.bookingbot.app.api.repository.TariffPriceRepository;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.Flight;
import ru.dartanum.bookingbot.domain.Seat;

@Component
@RequiredArgsConstructor
public class ChooseSeatClassAction implements CallbackQueryAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;
    private final FlightRepository flightRepository;
    private final TariffPriceRepository tariffPriceRepository;
    private final SeatPriceRepository seatPriceRepository;

    @Override
    public BotState execute(SendMessage sendMessage, CallbackQuery callbackQuery) {
        var seatClassCode = CallbackQueryAction.getCallbackParams(callbackQuery).get(0);
        var storage = userStorageRepository.getById(callbackQuery.getFrom().getId());

        var flight = flightRepository.findById(storage.getNewTicket().getFlightId());
        var tariffPrice = tariffPriceRepository.findByTariffId(storage.getNewTicket().getTariffId());
        var seat = getRandomSeatByClass(seatClassCode, flight);
        var seatPrice = seatPriceRepository.findByClassCodeAndTimetableId(seatClassCode, flight.getTimetable().getId());
        Long totalPrice = tariffPrice.getPrice() + seatPrice.getPrice();
        String currencySymbol = seatPrice.getCurrency().getSymbol();

        storage.getNewTicket().setSeatId(seat.getId());
        storage.getNewTicket().setPrice(totalPrice);
        userStorageRepository.save(storage);

        sendMessage.enableMarkdown(true);
        sendMessage.setText(MessageFactory.mdNewTicket(flight.getTimetable(), storage.getNewTicket().getPassenger(), seat.getCode(), totalPrice, currencySymbol));
        sendMessage.setReplyMarkup(KeyboardFactory.replyBookingDecisions());
        bookingBot.deleteMessage(callbackQuery.getMessage().getChatId(), callbackQuery.getMessage().getMessageId());

        return BotState.BOOKING_ticket_created;
    }

    private Seat getRandomSeatByClass(String seatClassCode, Flight flight) {
        return flight.getAirplane().getModel().getSeats().stream()
                .filter(seat -> seat.getTicket() == null)
                .filter(seat -> seat.getSeatClass().getCode().equals(seatClassCode))
                .findAny().orElse(null);
    }
}
