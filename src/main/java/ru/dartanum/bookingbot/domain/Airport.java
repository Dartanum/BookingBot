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
public class Airport {
    @Id
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String code;
    private String address;
    private String phoneNumber;
    private Boolean isInternational;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_code")
    @NotNull
    private City city;
}
