package io.pelat1.example.telegrambot.service.impl;

import io.pelat1.example.telegrambot.service.HelloBotService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class HelloBotServiceImpl implements HelloBotService {
    public SendMessage updateHandler(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(message)
                    .build();
        } else {
            return null;
        }
    }
}
