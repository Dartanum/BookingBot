package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.TicketRepository;
import ru.dartanum.bookingbot.domain.Ticket;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TicketJpaRepositoryAdapter implements TicketRepository {
    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public UUID save(Ticket ticket) {
        return ticketJpaRepository.save(ticket).getId();
    }

    @Override
    public List<Ticket> findAllByIds(Collection<UUID> ids) {
        return ticketJpaRepository.findAllById(ids);
    }
}
