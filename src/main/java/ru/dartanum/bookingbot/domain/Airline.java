package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
public class Airline {
    @Id
    private UUID id;
    @NotNull
    private String name;
    private String site;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] logo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_code")
    @NotNull
    private Country country;
}
