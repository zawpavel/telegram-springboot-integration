package com.integrationexample.updateprocessor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public record AnyMessageProcessor(TelegramBot telegramBot) implements UpdateProcessor {

    @Override
    public void process(Update update) {
        final var message = update.message();
        if (Objects.isNull(message) || Objects.isNull(message.text())) return;
        if (message.text().equals("/start") || message.text().equals("/help")) return;

        final var chatId = update.message().chat().id();
        telegramBot.execute(new SendMessage(chatId, "here is the place for any message"));
    }
}
