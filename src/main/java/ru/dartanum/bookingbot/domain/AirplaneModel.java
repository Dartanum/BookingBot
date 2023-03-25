package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
public class AirplaneModel {
    @Id
    private String code;
    private Integer seatNumber;
    private Integer maximumFlightRange;
    private Integer pathLength;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_code")
    @NotNull
    private AirplaneType type;

    @OneToMany(mappedBy = "airplaneModel")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Seat> seats;
}
