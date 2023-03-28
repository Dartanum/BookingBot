package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dartanum.bookingbot.domain.Country;

import java.util.List;

public interface CountryJpaRepository extends JpaRepository<Country, String> {
    @Query("from Country country where lower(country.name) like lower(concat(:name, '%'))")
    List<Country> searchByName(String name);
}
