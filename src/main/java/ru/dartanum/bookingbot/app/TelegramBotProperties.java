package ru.dartanum.bookingbot.app;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "telegram.bot")
public class TelegramBotProperties {
    private String token;
    private String username;
    private String webhookPath;
    private Long creatorId;
}
