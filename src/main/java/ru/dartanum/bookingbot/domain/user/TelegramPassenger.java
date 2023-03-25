package ru.dartanum.bookingbot.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.dartanum.bookingbot.domain.Country;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class TelegramPassenger {
    private UUID id;
    private String name;
    private String surname;
    private String fatherName;
    private String phoneNumber;
    private String sex;
    private String email;
    private LocalDate birthDate;
    private Set<Country> citizenship;

    public TelegramPassenger() {
        id = UUID.randomUUID();
        citizenship = new HashSet<>();
    }
}
