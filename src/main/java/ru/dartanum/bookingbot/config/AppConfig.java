package ru.dartanum.bookingbot.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "ru.dartanum.bookingbot")
@ComponentScan(basePackages = "ru.dartanum.bookingbot")
@EntityScan(basePackages = "ru.dartanum.bookingbot.domain")
@EnableJpaRepositories(basePackages = "ru.dartanum.bookingbot.adapter.persistence")
@EnableMongoRepositories(basePackages = "ru.dartanum.bookingbot.adapter.persistence.mongo")
public class AppConfig {
}
