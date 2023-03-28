package ru.dartanum.bookingbot.app.api.repository;

import ru.dartanum.bookingbot.domain.Tariff;
import ru.dartanum.bookingbot.domain.price.TariffPrice;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface TariffPriceRepository {
    TariffPrice findByTariffId(UUID id);

    Map<Tariff, TariffPrice> findAllByTariffs(Collection<Tariff> tariffs);
}
