package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Country;

import java.util.List;

public interface CountryRepository {
    List<Country> findAll();

    List<Country> searchByName(String name);
}
