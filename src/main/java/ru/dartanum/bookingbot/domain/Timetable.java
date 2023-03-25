package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Timetable {
    @Id
    private UUID id;
    private LocalDateTime departureDatetime;
    private LocalDateTime arrivalDatetime;
    private LocalDateTime registrationStartDatetime;
    private LocalDateTime registrationEndDatetime;
    private Boolean delayed;

    @OneToOne(optional = false)
    @JoinColumn(name = "flight_id")
    @NotNull
    private Flight flight;
}
