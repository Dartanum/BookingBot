package ru.dartanum.bookingbot.domain;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    @Transient
    private String typeName;

    @Setter
    @NotNull
    protected String name;

    public Place(Class<? extends Place> type) {
        this.typeName = type.getName();
    }

    public Place(String name) {
        this.name = name;
    }
}
