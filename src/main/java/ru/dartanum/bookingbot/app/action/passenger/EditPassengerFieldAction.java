package ru.dartanum.bookingbot.app.action.passenger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.BotState;
import ru.dartanum.bookingbot.app.MessageFactory;
import ru.dartanum.bookingbot.app.action.MessageAction;
import ru.dartanum.bookingbot.app.api.repository.CountryRepository;
import ru.dartanum.bookingbot.app.api.repository.UserStorageRepository;
import ru.dartanum.bookingbot.app.constant.Constants;
import ru.dartanum.bookingbot.domain.Country;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.*;
import static ru.dartanum.bookingbot.app.constant.CallbackActionConstants.EDIT_PASSENGER_FIELDS;
import static ru.dartanum.bookingbot.app.constant.RegularExpressions.*;
import static ru.dartanum.bookingbot.util.StringUtils.escape;
import static ru.dartanum.bookingbot.util.StringUtils.toWordListSeparatedByComma;

@Component
@RequiredArgsConstructor
public class EditPassengerFieldAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;
    private final CountryRepository countryRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        var userId = message.getFrom().getId();
        var value = message.getText();
        var storage = userStorageRepository.getById(userId);
        var fieldByPassengerId = storage.getEditingPassengerField();
        var field = fieldByPassengerId.getValue();
        var passenger = storage.getPassengerById(fieldByPassengerId.getKey());

        if (field.equals(EDIT_PASSENGER_FIELDS[0]) && value.matches(NAME_PART_REGEXP)) {
            passenger.setName(value);
        } else if (field.equals(EDIT_PASSENGER_FIELDS[1]) && value.matches(NAME_PART_REGEXP)) {
            passenger.setSurname(value);
        } else if (field.equals(EDIT_PASSENGER_FIELDS[2]) && value.matches(NAME_PART_REGEXP)) {
            passenger.setFatherName(value);
        } else if (field.equals(EDIT_PASSENGER_FIELDS[3]) && value.matches(PHONE_REGEXP)) {
            passenger.setPhoneNumber(value);
        } else if (field.equals(EDIT_PASSENGER_FIELDS[4]) && value.matches(EMAIL_REGEXP)) {
            passenger.setEmail(value);
        } else if (field.equals(EDIT_PASSENGER_FIELDS[5]) && value.matches(DATE_REGEXP)) {
            passenger.setBirthDate(LocalDate.parse(value, Constants.FLIGHT_DATE_TIME_FORMATTER));
        } else if (field.equals(EDIT_PASSENGER_FIELDS[6]) && value.matches(SEX_REGEXP)) {
            passenger.setSex(value);
        } else if (field.equals(EDIT_PASSENGER_FIELDS[7]) && value.matches(WORD_LIST_REGEXP)) {
            var countries = countryRepository.findAll();
            var countryNames = toWordListSeparatedByComma(value);
            var citizenship = getCitizenshipByEnteredName(countries, countryNames);

            if (citizenship.size() != countryNames.size()) {
                sendMessage.setText(MSG_TEMPLATE_INVALID_COUNTRY_NAMES);
                return BotState.EDITING_PASSENGER_enter_field;
            } else {
                passenger.setCitizenship(citizenship);
            }
        } else {
            sendMessage.setText(MSG_ERROR_DURING_EDITING_PASSENGER);

            return BotState.EDITING_PASSENGER_enter_field;
        }

        sendMessage.enableMarkdownV2(true);
        sendMessage.setText(escape(MSG_SUCCESS_EDITED_PASSENGER) + "\n\n" + MessageFactory.mdPassengerCard(passenger));

        return BotState.MENU;
    }

    private Set<Country> getCitizenshipByEnteredName(List<Country> countries, List<String> enteredCountryNames) {
        return countries.stream()
                .filter(country -> enteredCountryNames.contains(country.getName()))
                .collect(Collectors.toSet());
    }
}
