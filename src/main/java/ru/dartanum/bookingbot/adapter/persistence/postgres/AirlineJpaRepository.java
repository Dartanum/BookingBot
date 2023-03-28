package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dartanum.bookingbot.domain.Airline;

import java.util.UUID;

public interface AirlineJpaRepository extends JpaRepository<Airline, UUID> {
}
