package ru.dartanum.bookingbot.app;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
@Getter
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotProperties telegramBotProperties;
    private final UpdateHandler updateHandler;

    protected TelegramBot(TelegramBotProperties telegramBotProperties, UpdateHandler updateHandler) {
        super(telegramBotProperties.getToken());
        this.telegramBotProperties = telegramBotProperties;
        this.updateHandler = updateHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        updateHandler.handle(update, sendMessage);

        if (isNotBlank(sendMessage.getText())) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotProperties.getUsername();
    }

    @PostConstruct
    public void register() {
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
        } catch (TelegramApiException e) {
            log.error("Error during register bot");
        }
    }
}
