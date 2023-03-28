package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.AirportRepository;
import ru.dartanum.bookingbot.domain.Airport;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AirportJpaRepositoryAdapter implements AirportRepository {
    private final AirportJpaRepository airportJpaRepository;

    @Override
    public List<Airport> searchByName(String name) {
        return airportJpaRepository.searchByName(name);
    }
}
