package com.integrationexample.updateprocessor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public record CallbackProcessor(TelegramBot telegramBot) implements UpdateProcessor {

    @Override
    public void process(Update update) {
        if (Objects.isNull(update.callbackQuery())) return;

        final var chatId = update.callbackQuery().message().chat().id();
        telegramBot.execute(new SendMessage(chatId, "here is the place for callback reply message"));
        telegramBot.execute(new AnswerCallbackQuery(update.callbackQuery().id()));
    }
}
