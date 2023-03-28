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
import ru.dartanum.bookingbot.domain.Country;
import ru.dartanum.bookingbot.domain.user.TelegramPassenger;

import java.util.List;

import static java.lang.String.format;
import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_TEMPLATE_INVALID_COUNTRY_NAMES;
import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_TEMPLATE_PASSENGER_ADDED;
import static ru.dartanum.bookingbot.util.StringUtils.toWordListSeparatedByComma;

@Component
@RequiredArgsConstructor
public class EnterCitizenshipAction implements MessageAction {
    private final UserStorageRepository userStorageRepository;
    private final CountryRepository countryRepository;

    @Override
    public BotState execute(SendMessage sendMessage, Message message) {
        var storage = userStorageRepository.getById(message.getFrom().getId());
        var countries = countryRepository.findAll();
        var enteredCitizenshipList = toWordListSeparatedByComma(message.getText());
        var passenger = storage.getNewPassenger();

        var citizenshipList = getCitizenshipByEnteredName(countries, enteredCitizenshipList);
        storage.getNewPassenger().getCitizenship().addAll(citizenshipList);

        if (citizenshipList.size() != enteredCitizenshipList.size()) {
            var undefinedCountryNames = getUndefinedCountryNames(citizenshipList, enteredCitizenshipList);
            sendMessage.setText(format(MSG_TEMPLATE_INVALID_COUNTRY_NAMES, undefinedCountryNames.toString().replaceAll("[\\[\\]]", "")));

            return BotState.CREATING_PASSENGER_enterCitizenship;
        } else {
            storage.getPassengers().add(passenger);
            storage.setNewPassenger(new TelegramPassenger());
            sendMessage.enableMarkdownV2(true);
            sendMessage.setText(format(MSG_TEMPLATE_PASSENGER_ADDED, MessageFactory.mdPassengerCard(passenger)));
            userStorageRepository.save(storage);

            return BotState.PASSENGER_LIST;
        }
    }

    private List<Country> getCitizenshipByEnteredName(List<Country> countries, List<String> enteredCountryNames) {
        return countries.stream()
                .filter(country -> enteredCountryNames.contains(country.getName()))
                .toList();
    }

    private List<String> getUndefinedCountryNames(List<Country> definedCountries, List<String> enteredCountryNames) {
        var definedCountryNames = definedCountries.stream().map(Country::getName).toList();

        return enteredCountryNames.stream()
                .filter(country -> !definedCountryNames.contains(country))
                .toList();
    }
}
