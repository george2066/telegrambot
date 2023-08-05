package com.example.telegrambot.listener;

import com.example.telegrambot.service.BotCommandService;
import com.example.telegrambot.service.NotificationTaskService;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private NotificationTaskService notificationTaskService;
    @Mock
    private BotCommandService botCommandService;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Test
    @DisplayName("saveNotification")
    void shouldStartSaveNotificationIfSendMessageNotContainCommand() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("01.01.2022 20:00 Сделать домашнюю работу");
        telegramBotUpdatesListener.process(List.of(update));
        verify(notificationTaskService, atMostOnce()).saveNotification(message);
    }

    @Test
    @DisplayName("beginCommand")
    void shouldStartBeginCommandIfSendMessageContainCommand() {
        when(update.message()).thenReturn(message);
        when(message.entities()).thenReturn(
                new MessageEntity[]{new MessageEntity(
                        MessageEntity.Type.bot_command, 0, 6
                )}
        );
        when(message.text()).thenReturn("/start");
        telegramBotUpdatesListener.process(List.of(update));
        verify(botCommandService, atMostOnce()).beginCommand(message);
    }

}
