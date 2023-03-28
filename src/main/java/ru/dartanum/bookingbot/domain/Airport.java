package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Airport extends Place {
    @Id
    private UUID id;
    @NotNull
    private String code;
    private String address;
    private String phoneNumber;
    private Boolean isInternational;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_code")
    @NotNull
    private City city;

    public Airport() {
        super(Airport.class);
    }

    public Airport(UUID id, String name, String code, String address, String phoneNumber, Boolean isInternational, City city) {
        super(Airport.class);
        this.id = id;
        this.name = name;
        this.code = code;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isInternational = isInternational;
        this.city = city;
    }

    @Override
    public String toString() {
        return name;
    }
}
