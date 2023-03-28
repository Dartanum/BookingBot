package ru.dartanum.bookingbot.domain.price;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@RedisHash("seatprice")
@Data
@Accessors(chain = true)
public class SeatPrice {
    @Id
    @Indexed
    private UUID id;
    @Indexed
    private UUID timetableId;
    @Indexed
    private String classCode;
    private Long price;
    @Reference
    private Currency currency;
}
