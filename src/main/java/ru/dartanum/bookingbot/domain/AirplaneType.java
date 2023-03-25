package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class AirplaneType {
    @Id
    private String code;
    @NotNull
    private String name;
}
