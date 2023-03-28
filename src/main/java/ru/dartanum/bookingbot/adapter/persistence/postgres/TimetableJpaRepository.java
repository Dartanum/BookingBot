package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dartanum.bookingbot.domain.Timetable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TimetableJpaRepository extends JpaRepository<Timetable, UUID> {
    List<Timetable> findAllByDepartureDatetimeAfterAndDepartureDatetimeBefore(LocalDateTime start, LocalDateTime end);
}
