package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Ticket;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface TicketRepository {
    UUID save(Ticket ticket);

    List<Ticket> findAllByIds(Collection<UUID> ids);
}
