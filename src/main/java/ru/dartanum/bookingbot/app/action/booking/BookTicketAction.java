package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.KeyboardFactory;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.TicketRepository;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_TICKET_BOOKED;

@Component
@RequiredArgsConstructor
public class BookTicketAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;
    private final TicketRepository ticketRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        var storage = userStorageRepository.getById(message.getFrom().getId());
        var tgTicket = storage.getNewTicket();
        var tgPassenger = tgTicket.getPassenger();

        Passenger passenger = new Passenger()
                .setName(tgPassenger.getName())
                .setSurname(tgPassenger.getSurname())
                .setFatherName(tgPassenger.getFatherName())
                .setBirthDate(tgPassenger.getBirthDate())
                .setEmail(tgPassenger.getEmail())
                .setPhoneNumber(tgPassenger.getPhoneNumber())
                .setRegistrationDate(LocalDate.now())
                .setSex(tgPassenger.getSex())
                .setCitizenship(tgPassenger.getCitizenship());
        Ticket ticket = new Ticket()
                .setFlight(new Flight().setId(tgTicket.getFlightId()))
                .setPrice(tgTicket.getPrice().floatValue())
                .setCurrencySymbol(tgTicket.getCurrencySymbol())
                .setSeat(new Seat().setId(tgTicket.getSeatId()))
                .setTariff(new Tariff().setId(tgTicket.getTariffId()))
                .setOrderDatetime(LocalDateTime.now())
                .setPassenger(passenger);

        var ticketId = ticketRepository.save(ticket);
        storage.getTicketIds().add(ticketId);
        storage.reset();
        userStorageRepository.save(storage);

        sendMessage.setText(MSG_TICKET_BOOKED);
        sendMessage.setReplyMarkup(KeyboardFactory.replyMenuKeyboard());

        return BotState.MENU;
    }
}
