package ru.dartanum.bookingbot.domain.user;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.domain.Place;

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
    private Pair<Place, Place> bookingFromTo;
    private Pair<LocalDate, LocalDate> bookingDateRange;
    private Set<TelegramPassenger> passengers;
    private Set<UUID> ticketIds;
    private TelegramTicket newTicket;
    private Pair<UUID, String> editingPassengerField;
    private TelegramPassenger newPassenger;

    public UserStorage(Long userId) {
        this.userId = userId;
        botState = BotState.DEFAULT.name();
        passengers = new HashSet<>();
        newPassenger = new TelegramPassenger();
        newTicket = new TelegramTicket();
        ticketIds = new HashSet<>();
    }

    public void reset() {
        newTicket = new TelegramTicket();
        editingPassengerField = null;
        newPassenger = new TelegramPassenger();
        bookingFromTo = null;
        bookingDateRange = null;
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
