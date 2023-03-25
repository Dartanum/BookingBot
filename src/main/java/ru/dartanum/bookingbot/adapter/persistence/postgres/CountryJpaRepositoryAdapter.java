package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.CountryRepository;
import ru.dartanum.bookingbot.domain.Country;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CountryJpaRepositoryAdapter implements CountryRepository {
    private final CountryJpaRepository countryJpaRepository;

    @Override
    public List<Country> findAll() {
        return countryJpaRepository.findAll();
    }
}
