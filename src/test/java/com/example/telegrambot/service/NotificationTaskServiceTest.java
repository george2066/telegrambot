package com.example.telegrambot.service;

import com.example.telegrambot.model.NotificationTask;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import com.example.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationTaskServiceTest {

    @Autowired
    private NotificationTaskRepository repository;
    @Autowired
    private ParseService parseService;
    @Mock
    private Message message;
    @Mock
    private Chat chat;
    @InjectMocks
    @Autowired
    private NotificationTaskService service;

    private static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:15")
            .withUsername("user")
            .withPassword("user")
            .withDatabaseName("telegram_task_bot");

    static {
        POSTGRE_SQL_CONTAINER.start();
        System.setProperty("spring.datasource.url", String.valueOf(POSTGRE_SQL_CONTAINER.getJdbcUrl()));
    }

    @AfterEach
    public void afterEach() {
        repository.deleteAll();
    }

    @AfterAll
    public static void afterAll() {
        POSTGRE_SQL_CONTAINER.stop();
    }

    @Test
    void shouldSaveNotificationTaskWhenSaveNotificationCalled() {
        var notificationTask = NotificationTask.builder()
                .id(1L)
                .chatId(1L)
                .notification("Сделать домашнюю работу")
                .notificationDate(LocalDateTime.of(2022, 1, 1, 20, 0))
                .build();

        when(message.text()).thenReturn("01.01.2022 20:00 Сделать домашнюю работу");
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(1L);

        service.saveNotification(message);

        assertThat(repository.findById(1L)).isEqualTo(Optional.of(notificationTask));
    }

}
