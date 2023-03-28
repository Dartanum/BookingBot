package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.CityRepository;
import ru.dartanum.bookingbot.domain.City;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CityJpaRepositoryAdapter implements CityRepository {
    private final CityJpaRepository cityJpaRepository;

    @Override
    public List<City> searchByName(String name) {
        return cityJpaRepository.searchByName(name);
    }
}
