package com.example.telegrambot.configuration;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.generics.TelegramBot;


@Configuration
public class TelegramBotConfiguration {
    @Value("${telegram.bot.token}")
    private String token;
}
