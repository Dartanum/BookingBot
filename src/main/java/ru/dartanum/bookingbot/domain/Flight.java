package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Flight {
    @Id
    private UUID id;
    @NotNull
    private String code;

    @ManyToOne(optional = false)
    @JoinColumn(name = "route_id")
    @NotNull
    private Route route;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;
}
