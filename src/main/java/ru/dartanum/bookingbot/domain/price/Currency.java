package ru.dartanum.bookingbot.domain.price;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("currency")
@Data
public class Currency {
    @Id
    private String code;
    private String symbol;
    private String name;
}
