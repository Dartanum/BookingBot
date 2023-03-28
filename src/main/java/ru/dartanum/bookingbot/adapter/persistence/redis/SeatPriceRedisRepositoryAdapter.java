package ru.dartanum.bookingbot.adapter.persistence.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.SeatPriceRepository;
import ru.dartanum.bookingbot.domain.Timetable;
import ru.dartanum.bookingbot.domain.price.SeatPrice;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SeatPriceRedisRepositoryAdapter implements SeatPriceRepository {
    private final SeatPriceRedisRepository seatPriceRedisRepository;

    @Override
    public List<SeatPrice> findAllByTimetable(Timetable timetable) {
        return seatPriceRedisRepository.findAllByTimetableId(timetable.getId());
    }

    @Override
    public SeatPrice findByClassCodeAndTimetableId(String classCode, UUID timetableId) {
        return seatPriceRedisRepository.findByClassCodeAndTimetableId(classCode, timetableId).orElse(null);
    }
}
