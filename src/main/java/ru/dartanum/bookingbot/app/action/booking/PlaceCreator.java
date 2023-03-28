package ru.dartanum.bookingbot.app.action.booking;

import org.apache.commons.lang3.tuple.Pair;
import ru.dartanum.bookingbot.domain.Place;

import java.util.Arrays;

import static ru.dartanum.bookingbot.app.MessageFactory.placeClassByName;

public class PlaceCreator {
    private PlaceCreator() {};

    public static Place getPlaceByVariantNumber(String text, String variant) {
        var selectedRow = text.split("\n", -1)[Integer.parseInt(variant) + 1];
        var rowParts = selectedRow.split("\s");
        var place = String.join(" ", Arrays.copyOfRange(rowParts, 2, rowParts.length));
        var targetClass = placeClassByName.stream()
                .filter(pair -> pair.getKey().equals(rowParts[1]))
                .map(Pair::getValue)
                .findFirst().orElseThrow();

        return new Place(targetClass.getName(), place);
    }
}
