package ru.dartanum.bookingbot.adapter.persistence.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.TariffPriceRepository;
import ru.dartanum.bookingbot.domain.Tariff;
import ru.dartanum.bookingbot.domain.price.TariffPrice;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TariffPriceRedisRepositoryAdapter implements TariffPriceRepository {
    private final TariffPriceRedisRepository tariffPriceRedisRepository;

    @Override
    public TariffPrice findByTariffId(UUID id) {
        return tariffPriceRedisRepository.findById(id).orElse(null);
    }

    @Override
    public Map<Tariff, TariffPrice> findAllByTariffs(Collection<Tariff> tariffs) {
        return tariffs.stream().collect(Collectors.toMap(
                Function.identity(),
                tariff -> findByTariffId(tariff.getId())));
    }
}
