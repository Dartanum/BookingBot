package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Route {
    @Id
    private UUID id;
    @NotNull
    private String code;
    @Column(name = "flight_duration")
    private Integer flightDurationInMinutes;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_airport_id")
    @NotNull
    private Airport sourceAirport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "target_airport_id")
    @NotNull
    private Airport targetAirport;
}
