package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.City;

import java.util.List;

public interface CityRepository {
    List<City> searchByName(String name);
}
