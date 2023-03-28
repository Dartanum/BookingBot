package ru.dartanum.bookingbot.app;

import org.apache.commons.lang3.tuple.Pair;
import ru.dartanum.bookingbot.domain.*;
import ru.dartanum.bookingbot.domain.price.SeatPrice;
import ru.dartanum.bookingbot.domain.price.TariffPrice;
import ru.dartanum.bookingbot.domain.user.TelegramPassenger;
import ru.dartanum.bookingbot.util.DateTimeUtils;
import ru.dartanum.bookingbot.util.StringUtils;

import java.util.*;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static ru.dartanum.bookingbot.app.constant.Constants.FLIGHT_DATE_TIME_FORMATTER;
import static ru.dartanum.bookingbot.util.StringUtils.createFullName;
import static ru.dartanum.bookingbot.util.StringUtils.escape;

public class MessageFactory {
    public static final List<Pair<String, Class<? extends Place>>> placeClassByName = List.of(
            Pair.of("аэропорт", Airport.class),
            Pair.of("город", City.class),
            Pair.of("страна", Country.class));

    public static String mdPassengerCard(TelegramPassenger passenger) {
        var builder = new StringBuilder("*ФИО*: " + createFullName(passenger.getSurname(), passenger.getName(), passenger.getFatherName()));
        builder.append(isNotBlank(passenger.getSex()) ? "\n*Пол*: " + passenger.getSex() : "");
        builder.append(isNotBlank(passenger.getPhoneNumber()) ? "\n*Телефон*: " + escape(passenger.getPhoneNumber()) : "");
        builder.append(isNotBlank(passenger.getEmail()) ? "\n*Почта*: " + escape(passenger.getEmail()) : "");
        builder.append(!passenger.getCitizenship().isEmpty() ? "\n*Гражданство*: " + passenger.getCitizenship() : "");

        return builder.toString();
    }

    public static String mdPlaceSearchResult(List<Airport> airports, List<City> cities, List<Country> countries) {
        StringBuilder builder = new StringBuilder("Результаты поиска:\n\n");
        String rowTemplate = "*%d*\\. %s %s\n";
        int number = 0;

        if (!airports.isEmpty()) {
            for (Airport airport : airports) {
                builder.append(format(rowTemplate, ++number, placeClassByName.get(0).getKey(), escape(airport.getName())));
            }
        }
        if (!cities.isEmpty()) {
            for (City city : cities) {
                builder.append(format(rowTemplate, ++number, placeClassByName.get(1).getKey(), escape(city.getName())));
            }
        }
        if (!countries.isEmpty()) {
            for (Country country : countries) {
                builder.append(format(rowTemplate, ++number, placeClassByName.get(2).getKey(), escape(country.getName())));
            }
        }
        builder.append("\nЕсли в списке нет подходящего варианта введите другой запрос");

        return builder.toString();
    }

    public static String flightSearchEntry(Timetable timetable, Long price, String currencySymbol) {
        final String msgTemplate = """
                🛫 %s (%s, %s) ➡ %s (%s, %s) 🛬
                🕑 %s - %s
                ✈ %s
                В пути: %s
                Перевозчик: "%s"
                💵 %d %s
                """;
        var route = timetable.getFlight().getRoute();

        return format(msgTemplate,
                route.getSourceAirport(), route.getSourceAirport().getCity(), route.getSourceAirport().getCity().getCountry(),
                route.getTargetAirport(), route.getTargetAirport().getCity(), route.getTargetAirport().getCity().getCountry(),
                timetable.getDepartureDatetime().format(FLIGHT_DATE_TIME_FORMATTER), timetable.getArrivalDatetime().format(FLIGHT_DATE_TIME_FORMATTER),
                timetable.getFlight().getAirplane().getModel().getCode(),
                DateTimeUtils.formattedDurationBetween(timetable.getDepartureDatetime(), timetable.getArrivalDatetime()),
                timetable.getFlight().getAirplane().getAirline(),
                price, currencySymbol);
    }

    public static String mdPassengerList(Collection<TelegramPassenger> passengers) {
        StringBuilder builder = new StringBuilder("Выберите пассажира\n\n");
        int number = 0;

        for (var passenger : passengers) {
            builder.append(format("*%d*\\.\n", ++number) + mdPassengerCard(passenger) + "\n");
        }

        return builder.toString();
    }

    public static String mdTariffList(Map<Tariff, TariffPrice> tariffs) {
        final String msgTemplate = """
                *%d*.
                Тариф "%s"
                👜   Ручная кладь: %s
                🧳  Багаж: %s
                🔙💵 Возврат средств: %s
                🔄  Обмен билета: %s
                💵  Цена: *%d* %s
                
                """;
        StringBuilder builder = new StringBuilder("Выберите тариф\n\n");
        int number = 0;

        for (var tariffWithPrice : tariffs.entrySet()) {
            var tariff = tariffWithPrice.getKey();
            var price = tariffWithPrice.getValue();

            builder.append(escape(format(msgTemplate, ++number,
                    tariff.getName(),
                    tariff.isIncludeHandBaggage() ? tariff.getHandBaggageWeight() + " кг" : "\\-",
                    tariff.isIncludeBaggage() ? tariff.getBaggageWeight() + " кг" : "\\-",
                    tariff.isIncludeTicketRefund() ? "✅" : "❌",
                    tariff.isIncludeTicketExchange() ? "✅" : "❌",
                    price.getPrice(), price.getCurrency().getSymbol())));
        }

        return builder.toString();
    }

    public static String seatClasses(Map<String, SeatPrice> seatPriceByClassName) {
        final StringBuilder builder = new StringBuilder("Выберите место\n\n");
        final String msgRowTemplate = "%s класс - %d %s\n";

        seatPriceByClassName.forEach((key, value) -> builder.append(
                format(msgRowTemplate, key, value.getPrice(), value.getCurrency().getSymbol())));

        return builder.toString();
    }

    public static String mdNewTicket(Timetable timetable, TelegramPassenger passenger, String seatCode, Long price, String currencySymbol) {
        final String msgTemplate = """
                *Детали полета*
                🛫 %s (%s, %s) ➡ %s (%s, %s) 🛬
                🕑 %s ➡ %s
                ✈ %s
                В пути: %s
                Перевозчик: "%s"
                Место: %s
                
                *Пассажир*
                ФИО: %s
                Пол: %s
                Гражданство: %s
                
                *Стоимость*: %d %s
                """;
        var route = timetable.getFlight().getRoute();

        return format(msgTemplate,
                route.getSourceAirport(), route.getSourceAirport().getCity(), route.getSourceAirport().getCity().getCountry(),
                route.getTargetAirport(), route.getTargetAirport().getCity(), route.getTargetAirport().getCity().getCountry(),
                timetable.getDepartureDatetime().format(FLIGHT_DATE_TIME_FORMATTER), timetable.getArrivalDatetime().format(FLIGHT_DATE_TIME_FORMATTER),
                timetable.getFlight().getAirplane().getModel().getCode(),
                DateTimeUtils.formattedDurationBetween(timetable.getDepartureDatetime(), timetable.getArrivalDatetime()),
                timetable.getFlight().getAirplane().getAirline(),
                seatCode,
                StringUtils.createFullName(passenger.getSurname(), passenger.getName(), passenger.getFatherName()),
                passenger.getSex(),
                String.join(", ", passenger.getCitizenship().stream().map(Country::getName).toList()),
                price, currencySymbol);
    }

    public static String mdTicket(Ticket ticket) {
        final String msgTemplate = """
                *Рейс*: %s
                
                *Детали полета*
                🛫 %s (%s, %s) ➡ %s (%s, %s) 🛬
                🕑 %s ➡ %s
                ✈ %s
                В пути: %s
                Перевозчик: "%s"
                Место: %s
                Класс: %s
                
                *Пассажир*
                ФИО: %s
                Пол: %s
                Гражданство: %s
                
                *Стоимость*: %d %s
                """;
        var timetable = ticket.getFlight().getTimetable();
        var route = ticket.getFlight().getRoute();
        var passenger = ticket.getPassenger();

        return format(msgTemplate,
                ticket.getFlight().getCode(),
                route.getSourceAirport(), route.getSourceAirport().getCity(), route.getSourceAirport().getCity().getCountry(),
                route.getTargetAirport(), route.getTargetAirport().getCity(), route.getTargetAirport().getCity().getCountry(),
                timetable.getDepartureDatetime().format(FLIGHT_DATE_TIME_FORMATTER), timetable.getArrivalDatetime().format(FLIGHT_DATE_TIME_FORMATTER),
                timetable.getFlight().getAirplane().getModel().getCode(),
                DateTimeUtils.formattedDurationBetween(timetable.getDepartureDatetime(), timetable.getArrivalDatetime()),
                timetable.getFlight().getAirplane().getAirline(),
                ticket.getSeat().getCode(),
                ticket.getSeat().getSeatClass().getName(),
                StringUtils.createFullName(passenger.getSurname(), passenger.getName(), passenger.getFatherName()),
                passenger.getSex(),
                String.join(", ", passenger.getCitizenship().stream().map(Country::getName).toList()),
                ticket.getPrice().longValue(), ticket.getCurrencySymbol());
    }
}
