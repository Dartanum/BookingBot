package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.dartanum.bookingbot.app.BookingBot;
import ru.dartanum.bookingbot.app.KeyboardFactory;
import ru.dartanum.bookingbot.app.MessageFactory;
import ru.dartanum.bookingbot.app.api.repository.SeatPriceRepository;
import ru.dartanum.bookingbot.app.api.repository.TariffPriceRepository;
import ru.dartanum.bookingbot.app.api.repository.TimetableRepository;
import ru.dartanum.bookingbot.domain.Place;
import ru.dartanum.bookingbot.domain.price.SeatPrice;
import ru.dartanum.bookingbot.domain.price.TariffPrice;

import java.time.LocalDate;
import java.util.Comparator;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_EMPTY_SEARCH_RESULT;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TicketSearcher {
    private final BookingBot bookingBot;
    private final TimetableRepository timetableRepository;
    private final TariffPriceRepository tariffPriceRepository;
    private final SeatPriceRepository seatPriceRepository;

    public void searchAndSendResult(Long chatId, Pair<Place, Place> route, Pair<LocalDate, LocalDate> dates) {
        var timetableRows = timetableRepository.searchByDateAndFromTo(dates.getLeft(), route.getLeft(), route.getRight());

        if (timetableRows.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(MSG_EMPTY_SEARCH_RESULT);
            bookingBot.send(message);
        } else {
            timetableRows.forEach(timetable -> {
                var cheapestTariff = tariffPriceRepository.findAllByTariffs(timetable.getFlight().getAirplane().getAirline().getTariffs()).values()
                        .stream().min(Comparator.comparing(TariffPrice::getPrice)).orElse(null);
                var cheapestSeat = seatPriceRepository.findAllByTimetable(timetable)
                        .stream().min(Comparator.comparing(SeatPrice::getPrice)).orElse(null);
                var minPrice = cheapestTariff.getPrice() + cheapestSeat.getPrice();
                var currency = cheapestTariff.getCurrency();

                SendMessage message = new SendMessage();
                message.setChatId(chatId);
                message.setText(MessageFactory.flightSearchEntry(timetable, minPrice, currency.getSymbol()));
                message.setReplyMarkup(KeyboardFactory.inlineFlightOptions(timetable.getFlight().getId()));
                bookingBot.send(message);
            });
        }
    }
}
