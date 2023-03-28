package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dartanum.bookingbot.domain.Airport;

import java.util.List;
import java.util.UUID;

public interface AirportJpaRepository extends JpaRepository<Airport, UUID> {
    @Query("from Airport airport where lower(airport.name) like lower(concat(:name, '%'))")
    List<Airport> searchByName(String name);
}
