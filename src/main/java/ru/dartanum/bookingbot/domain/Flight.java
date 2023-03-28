package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Accessors(chain = true)
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

    @OneToOne(mappedBy = "flight")
    @ToString.Exclude
    private Timetable timetable;
}
