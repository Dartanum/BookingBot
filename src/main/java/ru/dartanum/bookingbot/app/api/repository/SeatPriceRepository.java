package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Timetable;
import ru.dartanum.bookingbot.domain.price.SeatPrice;

import java.util.List;
import java.util.UUID;

public interface SeatPriceRepository {
    List<SeatPrice> findAllByTimetable(Timetable timetable);

    SeatPrice findByClassCodeAndTimetableId(String code, UUID timetableId);
}
