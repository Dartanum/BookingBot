package ru.dartanum.bookingbot.domain.user;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.domain.Airport;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "userstorage")
@Data
@Accessors(chain = true)
public class UserStorage {
    @Id
    private Long userId;
    private String botState;
    private Pair<Airport, Airport> bookingAirportFromTo;
    private Pair<LocalDate, LocalDate> bookingDateRange;
    private Set<TelegramPassenger> passengers;
    private TelegramTicket ticket;
    private Pair<UUID, String> editingPassengerField;
    private TelegramPassenger newPassenger;

    public UserStorage(Long userId) {
        this.userId = userId;
        botState = BotState.DEFAULT.name();
        passengers = new HashSet<>();
        newPassenger = new TelegramPassenger();
    }

    public void reset() {
        editingPassengerField = null;
        newPassenger = new TelegramPassenger();
    }

    public BotState getBotState() {
        return BotState.valueOf(botState);
    }

    public UserStorage setBotState(BotState state) {
        botState = state.name();
        return this;
    }

    public TelegramPassenger getPassengerById(UUID id) {
        return passengers.stream().filter(passenger -> passenger.getId().equals(id)).findFirst().orElse(null);
    }

    public void removePassengerById(UUID id) {
        passengers.remove(getPassengerById(id));
    }
}
