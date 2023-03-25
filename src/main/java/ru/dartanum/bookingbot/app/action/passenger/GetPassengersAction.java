package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BookingBot;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.KeyboardFactory;
import ru.dartanum.bookingbot.app.MessageFactory;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.domain.user.TelegramPassenger;

import java.util.Collection;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_NO_PASSENGERS;

@Component
@RequiredArgsConstructor
public class GetPassengersAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;
    private final BookingBot bookingBot;

    @Override
    @Transactional(readOnly = true)
    public BotState execute(SendMessage sendMessage, Message message) {
        var userId = message.getFrom().getId();
        var passengers = userStorageRepository.getById(userId).getPassengers();

        if (passengers.isEmpty()) {
            sendMessage.setText(MSG_NO_PASSENGERS);
        } else {
            sendPassengerList(message.getChatId(), passengers);
            sendMessage.setText("Выберите действие");
        }

        sendMessage.setReplyMarkup(KeyboardFactory.replyAddPassengerKeyboard());

        return BotState.PASSENGER_LIST;
    }

    private void sendPassengerList(Long chatId, Collection<TelegramPassenger> passengers) {
        passengers.forEach(passenger -> {
            SendMessage msg = new SendMessage();
            msg.setChatId(chatId);
            msg.setText(MessageFactory.passengerCard(passenger));
            msg.enableMarkdownV2(true);
            msg.setReplyMarkup(KeyboardFactory.inlinePassengerOps(passenger.getId()));

            bookingBot.send(msg);
        });
    }
}
