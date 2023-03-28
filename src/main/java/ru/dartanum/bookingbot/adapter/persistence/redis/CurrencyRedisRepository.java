package ru.dartanum.bookingbot.adapter.persistence.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.dartanum.bookingbot.domain.price.Currency;

public interface CurrencyRedisRepository extends KeyValueRepository<Currency, String> {
}
