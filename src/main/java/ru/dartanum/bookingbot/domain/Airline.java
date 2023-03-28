package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
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

    @OneToMany(mappedBy = "airline", fetch = FetchType.EAGER)
    private List<Tariff> tariffs;

    @Override
    public String toString() {
        return name;
    }
}
