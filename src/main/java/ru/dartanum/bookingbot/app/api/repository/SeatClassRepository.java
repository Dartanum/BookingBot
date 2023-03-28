package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.SeatClass;

public interface SeatClassRepository {
    SeatClass findByCode(String code);
}
