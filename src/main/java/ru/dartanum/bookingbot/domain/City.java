package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class City {
    @Id
    private String code;
    @NotNull
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_id")
    @NotNull
    private Country country;
}
