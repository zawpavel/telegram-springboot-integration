package com.integrationexample;

import com.integrationexample.updateprocessor.CommonUpdateProcessor;
import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Component;

@Component
// you should choose what to use: UpdateListener or WebhookController and remove a component that you don't use
public class UpdateListener {

    public UpdateListener(TelegramBot telegramBot, CommonUpdateProcessor commonUpdateProcessor) {
        telegramBot.setUpdatesListener(updates -> {
            final var update = updates.get(0);
            commonUpdateProcessor.process(update);
            return update.updateId();
        });
    }
}