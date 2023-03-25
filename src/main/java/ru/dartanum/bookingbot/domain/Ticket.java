package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ticket {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "passenger_id")
    @NotNull
    private Passenger passenger;

    @ManyToOne(optional = false)
    @JoinColumn(name = "flight_id")
    @NotNull
    private Flight flight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tariff_id")
    @NotNull
    private Tariff tariff;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @NotNull
    private LocalDateTime orderDatetime;
    @NotNull
    private Float price;

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, "id");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }
}
