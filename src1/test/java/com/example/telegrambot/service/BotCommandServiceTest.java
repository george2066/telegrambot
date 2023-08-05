package com.example.telegrambot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BotCommandServiceTest {

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ParseService parse;
    @Mock
    private Message message;
    @InjectMocks
    private BotCommandService botCommandService;

    @Test
    void startCommandTest() {
        when(parse.command(message)).thenReturn("/start");
        when(message.chat()).thenReturn(new Chat());
        botCommandService.beginCommand(message);
        verify(telegramBot, atLeastOnce()).execute(any());
    }

    @Test
    void infoCommandTest() {
        when(parse.command(message)).thenReturn("/info");
        when(message.chat()).thenReturn(new Chat());
        botCommandService.beginCommand(message);
        verify(telegramBot, atLeastOnce()).execute(any());
    }

    @Test
    void anyCommandTest() {
        when(parse.command(message)).thenReturn("/NotExistCommand");
        when(message.chat()).thenReturn(new Chat());
        botCommandService.beginCommand(message);
        verify(telegramBot, atLeastOnce()).execute(any());
    }

}
