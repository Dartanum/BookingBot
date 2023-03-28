package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.FlightRepository;
import ru.dartanum.bookingbot.domain.Flight;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FlightJpaRepositoryAdapter implements FlightRepository {
    private final FlightJpaRepository flightJpaRepository;

    @Override
    public Flight findById(UUID id) {
        return flightJpaRepository.findById(id).orElse(null);
    }
}
