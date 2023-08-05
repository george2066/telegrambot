package com.example.telegrambot.exception;

public class InvalidNotificationFormatException extends Exception {
    @Override
    public String getMessage() {
        return "Не верный формат напоминания, воспользуйся /info";
    }
}
