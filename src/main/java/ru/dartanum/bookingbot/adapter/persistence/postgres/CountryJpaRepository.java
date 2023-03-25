package ru.dartanum.bookingbot.adapter.persistence.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dartanum.bookingbot.domain.Country;

public interface CountryJpaRepository extends JpaRepository<Country, String> {
}
