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

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class City extends Place {
    @Id
    private String code;

    @ManyToOne(optional = false)
    @JoinColumn(name = "country_code")
    @NotNull
    private Country country;

    public City() {
        super(City.class);
    }

    public City(String code, String name, Country country) {
        super(City.class);
        this.code = code;
        this.name = name;
        this.country = country;
    }

    @Override
    public String toString() {
        return name;
    }
}
