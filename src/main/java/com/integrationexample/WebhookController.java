package com.integrationexample;

import com.integrationexample.updateprocessor.CommonUpdateProcessor;
import com.pengrad.telegrambot.BotUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
// you should choose what to use: UpdateListener or WebhookController and remove a component that you don't use
public record WebhookController(CommonUpdateProcessor commonUpdateProcessor) {

    @PostMapping("make-your-very-secret-path-to-webhook")
    public void webhook(@RequestBody String payload) {
        final var update = BotUtils.parseUpdate(payload);
        commonUpdateProcessor.process(update);
    }

    @GetMapping("health")
    public String health() {
        return "ok";
    }
}
