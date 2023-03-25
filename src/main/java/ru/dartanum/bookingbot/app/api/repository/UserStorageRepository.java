package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.domain.user.UserStorage;

public interface UserStorageRepository {
    void save(UserStorage userStorage);

    UserStorage getById(Long userId);

    BotState getBotState(Long userId);

    void setBotState(Long userId, BotState state);

    void reset(Long userId);
}
