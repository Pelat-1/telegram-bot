package io.pelat1.example.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface HelloBotService {
    SendMessage updateHandler(Update update);
}
