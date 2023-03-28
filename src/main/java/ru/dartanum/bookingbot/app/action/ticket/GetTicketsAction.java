package ru.dartanum.bookingbot.app.action.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BookingBot;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.MessageFactory;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.TicketRepository;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;

@Component
@RequiredArgsConstructor
public class GetTicketsAction implements MessageAction {
    private final BookingBot bookingBot;
    private final UserStorageRepository userStorageRepository;
    private final TicketRepository ticketRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        var storage = userStorageRepository.getById(message.getFrom().getId());
        var tickets = ticketRepository.findAllByIds(storage.getTicketIds());

        tickets.forEach(ticket -> {
            SendMessage msg = new SendMessage();
            msg.setChatId(message.getChatId());
            msg.enableMarkdown(true);
            msg.setText(MessageFactory.mdTicket(ticket));
            bookingBot.send(msg);
        });

        return BotState.MENU;
    }
}
