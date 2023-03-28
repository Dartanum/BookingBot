package ru.dartanum.bookingbot.adapter.persistence.postgres;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.dartanum.bookingbot.app.api.repository.TimetableRepository;
import ru.dartanum.bookingbot.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TimetableJpaRepositoryAdapter implements TimetableRepository {
    private final TimetableJpaRepository timetableJpaRepository;

    @Override
    public Timetable findById(UUID id) {
        return timetableJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Timetable> searchByDateAndFromTo(LocalDate date, Place from, Place to) {
        var timetableByDate = timetableJpaRepository.findAllByDepartureDatetimeAfterAndDepartureDatetimeBefore(date.atStartOfDay(), date.plusDays(1).atStartOfDay());
        try {
            var classFrom = Class.forName(from.getTypeName());
            var classTo = Class.forName(to.getTypeName());

            if (classFrom.equals(Airport.class)) {
                timetableByDate = timetableByDate.stream()
                        .filter(tt -> tt.getFlight().getRoute().getSourceAirport().getName().equals(from.getName()))
                        .toList();
            } else if (classFrom.equals(City.class)) {
                timetableByDate = timetableByDate.stream()
                        .filter(tt -> tt.getFlight().getRoute().getSourceAirport().getCity().getName().equals(from.getName()))
                        .toList();
            } else if (classFrom.equals(Country.class)) {
                timetableByDate = timetableByDate.stream()
                        .filter(tt -> tt.getFlight().getRoute().getSourceAirport().getCity().getCountry().getName().equals(from.getName()))
                        .toList();
            }

            if (classTo.equals(Airport.class)) {
                timetableByDate = timetableByDate.stream()
                        .filter(tt -> tt.getFlight().getRoute().getTargetAirport().getName().equals(to.getName()))
                        .toList();
            } else if (classTo.equals(City.class)) {
                timetableByDate = timetableByDate.stream()
                        .filter(tt -> tt.getFlight().getRoute().getTargetAirport().getCity().getName().equals(to.getName()))
                        .toList();
            } else if (classTo.equals(Country.class)) {
                timetableByDate = timetableByDate.stream()
                        .filter(tt -> tt.getFlight().getRoute().getTargetAirport().getCity().getCountry().getName().equals(to.getName()))
                        .toList();
            }

            return timetableByDate;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
