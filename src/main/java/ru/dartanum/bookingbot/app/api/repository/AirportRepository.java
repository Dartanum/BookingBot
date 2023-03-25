package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Airport;

import java.util.List;

public interface AirportRepository {
    List<Airport> search(String text);
}
