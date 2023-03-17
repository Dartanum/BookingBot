package ru.dartanum.bookingbot.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = "ru.dartanum.bookingbot")
@ComponentScan(basePackages = "ru.dartanum.bookingbot")
public class AppConfig {
}
