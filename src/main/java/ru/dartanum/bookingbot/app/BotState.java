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
import ru.dartanum.bookingbot.app.action.booking.*;
import ru.dartanum.bookingbot.app.action.passenger.*;
import ru.dartanum.bookingbot.app.action.ticket.GetTicketsAction;

import java.util.EnumSet;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static ru.dartanum.bookingbot.app.constant.CallbackActionConstants.*;
import static ru.dartanum.bookingbot.app.constant.MessageActionConstants.*;
import static ru.dartanum.bookingbot.app.constant.RegularExpressions.*;

@Getter
public enum BotState {
    CALLBACK_QUERY {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_DELETE_PASSENGER)) {
                return context.getBean(DeletePassengerAction.class);
            }
            if (text.startsWith(ACT_EDIT_PASSENGER)) {
                return context.getBean(StartEditPassengerAction.class);
            }
            return null;
        }
    },

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

    //---------------------------PASSENGER---------------------------

    PASSENGER_LIST {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            return switch (text) {
                case ACT_ADD_PASSENGER -> context.getBean(CreatePassengerAction.class);
                case ACT_EDIT_PASSENGER -> context.getBean(StartEditPassengerAction.class);
                case ACT_DELETE_PASSENGER -> context.getBean(DeletePassengerAction.class);
                default -> null;
            };
        }
    },

    CREATING_PASSENGER_enterFullName {
        @Override
        Action selectAction(String text) {
            if (isNotBlank(text)) {
                var partNumber = text.split(" ").length;
                if (partNumber == 2 || partNumber == 3) {
                    return getApplicationContext().getBean(EnterFullNameAction.class);
                }
            }
            return null;
        }
    },

    CREATING_PASSENGER_enterSex {
        @Override
        Action selectAction(String text) {
            if (isNotBlank(text) && (text.trim().equalsIgnoreCase("М") || text.trim().equalsIgnoreCase(("Ж")))) {
                return getApplicationContext().getBean(EnterSexAction.class);
            }
            return null;
        }
    },

    CREATING_PASSENGER_enterBirthDate {
        @Override
        Action selectAction(String text) {
            if (isNotBlank(text) && text.trim().matches(DATE_REGEXP)) {
                return getApplicationContext().getBean(EnterBirthDateAction.class);
            }
            return null;
        }
    },

    CREATING_PASSENGER_enterPhoneNumber {
        @Override
        Action selectAction(String text) {
            if (isNotBlank(text) && text.trim().matches(PHONE_REGEXP)) {
                return getApplicationContext().getBean(EnterPhoneNumberAction.class);
            }
            return null;
        }
    },

    CREATING_PASSENGER_enterEmail {
        @Override
        Action selectAction(String text) {
            if (isNotBlank(text) && text.trim().matches(EMAIL_REGEXP)) {
                return getApplicationContext().getBean(EnterEmailAction.class);
            }
            return null;
        }
    },

    CREATING_PASSENGER_enterCitizenship {
        @Override
        Action selectAction(String text) {
            if (isNotBlank(text) && text.trim().matches(WORD_LIST_REGEXP)) {
                return getApplicationContext().getBean(EnterCitizenshipAction.class);
            }
            return null;
        }
    },

    EDITING_PASSENGER_choose_field {
        @Override
        Action selectAction(String text) {
            if (text.startsWith(ACT_EDIT_PASSENGER_CHOOSE_FIELD)) {
                return getApplicationContext().getBean(EditPassengerChooseFieldAction.class);
            }
            return null;
        }
    },

    EDITING_PASSENGER_enter_field {
        @Override
        Action selectAction(String text) {
            if (text.length() > 0) {
                return getApplicationContext().getBean(EditPassengerFieldAction.class);
            }
            return null;
        }
    },

    //---------------------------BOOKING---------------------------

    BOOKING_start {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.matches(DATE_RANGE_REGEXP)) {
                return context.getBean(ChooseBookingDateAction.class);
            }
            return null;
        }
    },

    BOOKING_dates_chosen {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_BOOKING_CHOOSE_VARIANT)) {
                return context.getBean(ChooseSourceAction.class);
            }
            if (isNotBlank(text) && text.length() > 3) {
                return context.getBean(EnterSourceAction.class);
            }
            return null;
        }
    },

    BOOKING_source_place_chosen {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_BOOKING_CHOOSE_VARIANT)) {
                return context.getBean(ChooseDestinationAction.class);
            }
            if (isNotBlank(text) && text.length() > 3) {
                return context.getBean(EnterDestinationAction.class);
            }
            return null;
        }
    },

    BOOKING_destination_place_chosen {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_START_TICKET_BOOKING)) {
                return context.getBean(StartBookTicketAction.class);
            }
            return null;
        }
    },

    BOOKING_flight_chosen {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_BOOKING_CHOOSE_VARIANT)) {
                return context.getBean(ChoosePassengerAction.class);
            }
            return null;
        }
    },

    BOOKING_passenger_chosen {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_BOOKING_CHOOSE_VARIANT)) {
                return context.getBean(ChooseTariffAction.class);
            }
            return null;
        }
    },

    BOOKING_tariff_chosen {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_BOOKING_CHOOSE_VARIANT)) {
                return context.getBean(ChooseSeatClassAction.class);
            }
            return null;
        }
    },

    BOOKING_ticket_created {
        @Override
        Action selectAction(String text) {
            var context = getApplicationContext();
            if (text.startsWith(ACT_BOOK_TICKET)) {
                return context.getBean(BookTicketAction.class);
            }
            return null;
        }
    };

    private ApplicationContext applicationContext;

    public BotState nextState(SendMessage sendMessage, Message message) {
        MessageAction action = (MessageAction) selectAction(message.getText());

        return action != null
                ? action.execute(sendMessage, message)
                : getApplicationContext().getBean(IncorrectEnteredDataAction.class).execute(sendMessage, message);
    }

    public BotState nextState(SendMessage sendMessage, CallbackQuery callbackQuery) {
        CallbackQueryAction action = (CallbackQueryAction) selectAction(callbackQuery.getData());
        Message msgFromCallback  = new Message();
        msgFromCallback.setFrom(callbackQuery.getFrom());

        return action != null
                ? action.execute(sendMessage, callbackQuery)
                : CALLBACK_QUERY;
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
