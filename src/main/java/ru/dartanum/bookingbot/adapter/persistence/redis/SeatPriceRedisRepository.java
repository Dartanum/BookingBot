package ru.dartanum.bookingbot.adapter.persistence.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.dartanum.bookingbot.domain.price.SeatPrice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeatPriceRedisRepository extends KeyValueRepository<SeatPrice, UUID> {
    List<SeatPrice> findAllByTimetableId(UUID timetableId);

    Optional<SeatPrice> findByClassCodeAndTimetableId(String classCode, UUID timetableId);
}
