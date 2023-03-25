package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Seat {
    @Id
    private UUID id;
    @NotNull
    private String code;

    @ManyToOne(optional = false)
    @JoinColumn(name = "class_code")
    @NotNull
    private SeatClass seatClass;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_model_code")
    @NotNull
    private AirplaneModel airplaneModel;
}
