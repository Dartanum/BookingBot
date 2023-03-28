package ru.dartanum.bookingbot.domain.price;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@RedisHash("tariffprice")
@Data
public class TariffPrice {
    @Id
    private UUID tariffId;
    private Long price;
    @Reference
    private Currency currency;
}
