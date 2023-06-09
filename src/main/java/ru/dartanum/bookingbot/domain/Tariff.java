package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
public class Tariff {
    @Id
    private UUID id;
    @NotNull
    private String name;
    private boolean includeHandBaggage;
    private Float handBaggageWeight;
    private boolean includeBaggage;
    private Float baggageWeight;
    private boolean includeTicketExchange;
    private boolean includeTicketRefund;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airline_id")
    @NotNull
    private Airline airline;
}
