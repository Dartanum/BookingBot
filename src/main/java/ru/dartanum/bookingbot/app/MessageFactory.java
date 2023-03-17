package ru.dartanum.bookingbot.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.abilitybots.api.sender.SilentSender;

import static ru.dartanum.bookingbot.app.constant.Action.GET_PASSENGERS;

@Component
@RequiredArgsConstructor
public class MessageFactory {
   // private final SilentSender sender;

//    public void actionGetPassengersHandler(long chatId, String action) {
//        try {
//            switch (action) {
//                case GET_PASSENGERS:
//                    sender.send("list of passengers", chatId);
//            }
//        }
//    }
}
