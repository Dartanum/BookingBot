package ru.dartanum.bookingbot.app;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.action.*;

import java.util.EnumSet;

import static ru.dartanum.bookingbot.app.constant.ActionConstants.*;
import static ru.dartanum.bookingbot.app.constant.RegularExpressions.DATE_RANGE_REGEXP;

@Getter
public enum BotState {
    DEFAULT {
        @Override
        Action selectAction(String text) {
            if (text.equals(ACT_START)) {
                return getApplicationContext().getBean(StartAction.class);
            }
            return null;
        }
    },

    MENU {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            return switch (text) {
                case ACT_GET_TICKETS -> context.getBean(GetTicketsAction.class);
                case ACT_GET_PASSENGERS -> context.getBean(GetPassengersAction.class);
                case ACT_START_BOOKING -> context.getBean(StartBookingAction.class);
                default -> null;
            };
        }
    },

    PASSENGER_LIST {
        @Override
        Action selectAction(String text) {
            return getApplicationContext().getBean(GoToMenuAction.class);
        }
    },

    TICKET_LIST {
        @Override
        Action selectAction(String text) {
            return getApplicationContext().getBean(GoToMenuAction.class);
        }
    },

    START_BOOKING {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.matches(DATE_RANGE_REGEXP)) {
                return context.getBean(ChooseBookingDateRangeAction.class);
            }
            return context.getBean(ReplyToWrongBookingDateRange.class);
        }
    },

    BOOKING_DATE_CHOSEN {
        @Override
        Action selectAction(String text) {
            return getApplicationContext().getBean(GoToMenuAction.class);
        }
    };

    private ApplicationContext applicationContext;

    public BotState nextState(SendMessage sendMessage, Message message) {
        MessageAction action = (MessageAction) selectAction(message.getText());

        return action.execute(sendMessage, message);
    }

    public BotState nextState(SendMessage sendMessage, CallbackQuery callbackQuery) {
        CallbackQueryAction action = (CallbackQueryAction) selectAction(callbackQuery.getData());

        return action.execute(sendMessage, callbackQuery);
    }

    abstract Action selectAction(String text);

    public void setContext(ApplicationContext context) {
        applicationContext = context;
    }

    @Component
    public static class ApplicationContextInjector {
        @Autowired
        private ApplicationContext applicationContext;

        @PostConstruct
        public void inject() {
            for (BotState state : EnumSet.allOf(BotState.class)) {
                state.setContext(applicationContext);
            }
        }
    }
}
