package ru.dartanum.bookingbot.app;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.*;
import org.telegram.abilitybots.api.toggle.BareboneToggle;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Optional;

import static org.telegram.abilitybots.api.objects.Flag.CALLBACK_QUERY;
import static ru.dartanum.bookingbot.app.constant.Action.GET_PASSENGERS;
import static ru.dartanum.bookingbot.app.constant.Action.GET_TICKETS;
import static ru.dartanum.bookingbot.app.constant.BotReplyConstants.*;

@Component
@Getter
@Slf4j
public class TelegramBot extends AbilityBot {
    private final Long CREATOR_ID;
    private final TelegramBotProperties telegramBotProperties;

    protected TelegramBot(TelegramBotProperties telegramBotProperties) {
        super(telegramBotProperties.getToken(), telegramBotProperties.getUsername(), new BareboneToggle());
        this.telegramBotProperties = telegramBotProperties;
        this.CREATOR_ID = telegramBotProperties.getCreatorId();
    }

    @Override
    public long creatorId() {
        return CREATOR_ID;
    }

    public Ability replyToStart() {
        return Ability.builder()
                .name("start")
                .info("start conversation with bot")
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(messageContext -> silent.execute(SendMessage.builder()
                        .text(MSG_START)
                        .chatId(messageContext.chatId())
                        .replyMarkup(KeyboardFactory.menuButtons())
                        .build()))
                .build();
    }

    public ReplyFlow getPassengersFlow() {
        return ReplyFlow.builder(db)
                .action((baseAbilityBot, update) -> silent.send("list of passengers", update.getCallbackQuery().getMessage().getChatId()))
                .onlyIf(update -> update.hasCallbackQuery() && update.getCallbackQuery().getData().equals(GET_PASSENGERS))
                .build();
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
