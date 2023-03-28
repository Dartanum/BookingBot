package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dartanum.bookingbot.domain.Flight;

import java.util.UUID;

public interface FlightJpaRepository extends JpaRepository<Flight, UUID> {
}
