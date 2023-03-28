package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Place;
import ru.dartanum.bookingbot.domain.Timetable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TimetableRepository {
    Timetable findById(UUID id);

    List<Timetable> searchByDateAndFromTo(LocalDate date, Place from, Place to);
}
