package ru.dartanum.bookingbot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dartanum.bookingbot.adapter.persistence.postgres.AirlineJpaRepository;
import ru.dartanum.bookingbot.adapter.persistence.postgres.TimetableJpaRepository;
import ru.dartanum.bookingbot.adapter.persistence.redis.CurrencyRedisRepository;
import ru.dartanum.bookingbot.adapter.persistence.redis.SeatPriceRedisRepository;
import ru.dartanum.bookingbot.adapter.persistence.redis.TariffPriceRedisRepository;
import ru.dartanum.bookingbot.domain.price.Currency;
import ru.dartanum.bookingbot.domain.price.SeatPrice;
import ru.dartanum.bookingbot.domain.price.TariffPrice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest(classes = BookingBotApplication.class)
public class FillPrices {

    @Autowired
    private AirlineJpaRepository airlineJpaRepository;
    @Autowired
    private TimetableJpaRepository timetableJpaRepository;
    @Autowired
    private TariffPriceRedisRepository tariffPriceRedisRepository;
    @Autowired
    private SeatPriceRedisRepository seatPriceRedisRepository;
    @Autowired
    private CurrencyRedisRepository currencyRedisRepository;

    @BeforeEach
    void reset() {
        tariffPriceRedisRepository.deleteAll();
        seatPriceRedisRepository.deleteAll();
        currencyRedisRepository.deleteAll();
    }

    @Test
    void execute() {
        Currency rubCurrency = new Currency();
        rubCurrency.setName("Рубль");
        rubCurrency.setCode("RUB");
        rubCurrency.setSymbol("₽");
        currencyRedisRepository.save(rubCurrency);

        Currency dollarCurrency = new Currency();
        dollarCurrency.setName("Американский доллар");
        dollarCurrency.setCode("USD");
        dollarCurrency.setSymbol("$");
        currencyRedisRepository.save(dollarCurrency);

        final String ECONOMY_CLASS = "E";
        final String BUSINESS_CLASS = "B";
        final String FIRST_CLASS = "F";
        final List<String> seatClasses = new ArrayList<>() {{add(ECONOMY_CLASS); add(BUSINESS_CLASS); add(FIRST_CLASS);}};

        var priceFactorBySeatClass = Map.of(ECONOMY_CLASS, 1.0, BUSINESS_CLASS, 2.5, FIRST_CLASS, 4.0);
        var timetables = timetableJpaRepository.findAll();

        timetables.forEach(timetable -> {
            var randomBasePrice = new Random().nextInt(1000) + 1000;

            seatClasses.forEach(seatClass -> {
                SeatPrice seatPrice = new SeatPrice();
                seatPrice.setTimetableId(timetable.getId());
                seatPrice.setClassCode(seatClass);
                seatPrice.setPrice(Math.round(randomBasePrice * priceFactorBySeatClass.get(seatClass)));
                seatPrice.setCurrency(rubCurrency);
                seatPriceRedisRepository.save(seatPrice);
            });
        });

        var airlines = airlineJpaRepository.findAll();

        airlines.forEach(airline -> {
            var randomBasePrice = new Random().nextInt(1000) + 500;

            airline.getTariffs().forEach(tariff -> {
                TariffPrice tariffPrice = new TariffPrice();
                tariffPrice.setTariffId(tariff.getId());
                tariffPrice.setCurrency(rubCurrency);
                tariffPrice.setPrice(Math.round(
                        tariff.isIncludeBaggage() && tariff.isIncludeTicketExchange() ? randomBasePrice * 3.8
                        : tariff.isIncludeBaggage() && !tariff.isIncludeTicketRefund() ? randomBasePrice * 2.6
                        : randomBasePrice * 2.2));
                tariffPriceRedisRepository.save(tariffPrice);
            });
        });
    }


}
