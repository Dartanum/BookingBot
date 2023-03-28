package ru.dartanum.bookingbot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class Passenger {
    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String fatherName;
    private String phoneNumber;
    @NotNull
    private String sex;
    @Email
    private String email;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private LocalDate registrationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "citizenship",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "country_code"))
    private Set<Country> citizenship = new HashSet<>();

    @OneToMany(mappedBy = "passenger", fetch = FetchType.EAGER, orphanRemoval = true, cascade = {CascadeType.ALL})
    private List<IdentityDocument> documents = new ArrayList<>();

    @OneToMany(mappedBy = "passenger")
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, "id");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }
}
