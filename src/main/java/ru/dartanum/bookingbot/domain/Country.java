package ru.dartanum.bookingbot.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Accessors(chain = true)
@Data
public class Country {
    @Id
    private String code;
    @NotNull
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
