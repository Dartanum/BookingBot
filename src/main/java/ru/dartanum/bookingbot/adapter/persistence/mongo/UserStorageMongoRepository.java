package ru.dartanum.bookingbot.adapter.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dartanum.bookingbot.domain.user.UserStorage;

public interface UserStorageMongoRepository extends MongoRepository<UserStorage, Long> {
}
