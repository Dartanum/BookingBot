package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dartanum.bookingbot.domain.Ticket;

import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<Ticket, UUID> {
}
