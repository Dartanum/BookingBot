package ru.dartanum.bookingbot.adapter.persistence.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.user.UserStorage;

@Component
@RequiredArgsConstructor
public class UserStorageRepositoryAdapter implements UserStorageRepository {
    private final UserStorageMongoRepository userStorageMongoRepository;

    @Override
    public void save(UserStorage userStorage) {
        userStorageMongoRepository.save(userStorage);
    }

    @Override
    public UserStorage getById(Long userId) {
        return findByUserIdOrElseSave(userId);
    }

    @Override
    public BotState getBotState(Long userId) {
        return findByUserIdOrElseSave(userId).getBotState();
    }

    @Override
    public void setBotState(Long userId, BotState state) {
        userStorageMongoRepository.save(getById(userId).setBotState(state));
    }

    @Override
    public void reset(Long userId) {
        userStorageMongoRepository.findById(userId).ifPresent(
                userStorage -> {
                    userStorage.reset();
                    userStorageMongoRepository.save(userStorage);
                }
        );
    }

    private UserStorage findByUserIdOrElseSave(Long userId) {
        var flow = userStorageMongoRepository.findById(userId);
        return flow.orElseGet(() -> userStorageMongoRepository.save(new UserStorage(userId)));
    }
}
