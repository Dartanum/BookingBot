package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dartanum.bookingbot.domain.City;

import java.util.List;

public interface CityJpaRepository extends JpaRepository<City, String> {
    @Query("from City city where lower(city.name) like lower(concat(:name, '%'))")
    List<City> searchByName(String name);
}
