package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Flight;

import java.util.UUID;

public interface FlightRepository {
    Flight findById(UUID id);
}
