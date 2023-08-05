package com.example.telegrambot.service;

import com.example.telegrambot.exception.InvalidNotificationFormatException;
import com.example.telegrambot.model.NotificationTask;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.MessageEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParseServiceTest {
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    private ParseService parse;

    @Test
    @DisplayName("notification [positive]")
    void shouldReturnNotificationTaskIfSendMessageCorrect() throws InvalidNotificationFormatException {
        var expected = NotificationTask.builder()
                .id(null)
                .chatId(1L)
                .notification("Сделать домашнюю работу")
                .notificationDate(LocalDateTime.of(2022, 1, 1, 20, 0))
                .build();
        when(message.text()).thenReturn("01.01.2022 20:00 Сделать домашнюю работу");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);
        assertThat(parse.notification(message)).isEqualTo(expected);
    }

    @Test
    @DisplayName("notification [negative]")
    void shouldThrowInvalidNotificationFormatExceptionIfSendMessageNotCorrect() {
        when(message.text()).thenReturn("01.01.22 20:00 Сделать домашнюю работу");
        assertThatExceptionOfType(InvalidNotificationFormatException.class)
                .isThrownBy(() -> parse.notification(message));
    }

    @Test
    @DisplayName("command")
    void shouldReturnCorrectBotCommand() {
        when(message.entities()).thenReturn(
                new MessageEntity[]{new MessageEntity(
                        MessageEntity.Type.bot_command, 0, 6
                )}
        );
        when(message.text()).thenReturn("/start");
        assertThat(parse.command(message)).isEqualTo("/start");

        when(message.text()).thenReturn("/start 5a6s5d4");
        assertThat(parse.command(message)).isEqualTo("/start");
    }

}
