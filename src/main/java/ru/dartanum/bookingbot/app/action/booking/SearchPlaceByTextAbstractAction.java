package ru.dartanum.bookingbot.app.action.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.dartanum.bookingbot.app.KeyboardFactory;
import ru.dartanum.bookingbot.app.MessageFactory;
import ru.dartanum.bookingbot.app.api.repository.AirportRepository;
import ru.dartanum.bookingbot.app.api.repository.CityRepository;
import ru.dartanum.bookingbot.app.api.repository.CountryRepository;

import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.MSG_CANNOT_FIND_AIRPORTS;

@Component
@RequiredArgsConstructor
public class SearchPlaceByTextAbstractAction { //todo REFACTORING
    private final AirportRepository airportRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public void execute(SendMessage sendMessage, Message message) {
        var searchText = message.getText().trim();

        var foundedAirports = airportRepository.searchByName(searchText);
        var foundedCities = cityRepository.searchByName(searchText);
        var foundedCountries = countryRepository.searchByName(searchText);
        var totalResult = foundedAirports.size() + foundedCountries.size() + foundedCities.size();

        if (foundedAirports.isEmpty() && foundedCities.isEmpty() && foundedCountries.isEmpty()) {
            sendMessage.setText(MSG_CANNOT_FIND_AIRPORTS);
        } else {
            sendMessage.enableMarkdownV2(true);
            sendMessage.setText(MessageFactory.mdPlaceSearchResult(foundedAirports, foundedCities, foundedCountries));
            sendMessage.setReplyMarkup(KeyboardFactory.inlineListOfVariants(totalResult));
        }
    }
}
