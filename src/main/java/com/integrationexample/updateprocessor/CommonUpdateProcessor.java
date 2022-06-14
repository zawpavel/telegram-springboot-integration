package com.integrationexample.updateprocessor;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public record CommonUpdateProcessor(TelegramBot telegramBot, List<UpdateProcessor> updateProcessors) {

    public void process(final Update update) {
        for (UpdateProcessor updateProcessor : updateProcessors) {
            updateProcessor.process(update);
        }
    }
}