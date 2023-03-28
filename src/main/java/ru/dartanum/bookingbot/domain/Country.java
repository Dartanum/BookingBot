package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Country extends Place {
    @Id
    private String code;

    public Country() {
        super(Country.class);
    }

    public Country(String code, String name) {
        super(Country.class);
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
