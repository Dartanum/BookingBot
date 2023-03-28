package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dartanum.bookingbot.domain.SeatClass;

public interface SeatClassJpaRepository extends JpaRepository<SeatClass, String> {
}
