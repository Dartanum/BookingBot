package ru.dartanum.bookingbot.adapter.persistence.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.dartanum.bookingbot.domain.price.TariffPrice;

import java.util.UUID;

public interface TariffPriceRedisRepository extends KeyValueRepository<TariffPrice, UUID> {
}
