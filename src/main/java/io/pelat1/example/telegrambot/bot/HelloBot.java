package io.pelat1.example.telegrambot.bot;

import io.pelat1.example.telegrambot.service.HelloBotService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
@Slf4j
public class HelloBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
    @Getter
    private final String botName;
    @Getter
    private final String botUsername;
    @Getter
    private final String botToken;
    private final HelloBotService helloBotService;
    private final TelegramClient telegramClient;

    @Autowired
    public HelloBot(
            @Value("${bot.name}") String botName,
            @Value("${bot.username}") String botUsername,
            @Value("${bot.token}") String botToken,
            HelloBotService helloBotService) {
        this.botName = botName;
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.helloBotService = helloBotService;
        this.telegramClient = new OkHttpTelegramClient(this.botToken);
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        log.info("{}", update);
        SendMessage sm = helloBotService.updateHandler(update);
        if (sm != null) {
            log.info("{}", sm);
            try {
                telegramClient.execute(sm);
            } catch (TelegramApiException e) {
                log.error("", e);
            }
        }
    }
}
