package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Airplane {
    @Id
    private UUID id;
    @NotNull
    private String code;
    private Integer numberOfFlights;
    private LocalDate manufactureDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_code")
    @NotNull
    private AirplaneModel model;

    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;
}
