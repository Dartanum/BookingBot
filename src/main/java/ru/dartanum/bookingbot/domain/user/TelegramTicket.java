package ru.dartanum.bookingbot.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramTicket {
    private UUID id;
    private TelegramPassenger passenger;
    private UUID flightId;
    private UUID tariffId;
    private UUID seatId;
    private Float price;
}
