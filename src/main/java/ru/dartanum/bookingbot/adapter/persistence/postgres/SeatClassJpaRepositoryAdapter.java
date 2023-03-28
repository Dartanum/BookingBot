package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.SeatClassRepository;
import ru.dartanum.bookingbot.domain.SeatClass;

@Repository
@RequiredArgsConstructor
public class SeatClassJpaRepositoryAdapter implements SeatClassRepository {
    private final SeatClassJpaRepository seatClassJpaRepository;

    @Override
    public SeatClass findByCode(String code) {
        return seatClassJpaRepository.findById(code).orElse(null);
    }
}
