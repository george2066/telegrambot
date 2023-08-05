package com.example.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.telegrambot.service.NotificationTaskService;
import com.example.telegrambot.service.BotCommandService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final NotificationTaskService notificationTaskService;
    private final BotCommandService botCommandService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Processing update: {}", update);
            var message = update.message();
            if (message != null) {
                if (message.text() != null && message.entities() == null) {
                    notificationTaskService.saveNotification(message);
                }
                if (message.text() != null && message.entities() != null) {
                    botCommandService.beginCommand(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}