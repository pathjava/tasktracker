package ru.progwards.tasktracker.controller.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String id) {
        super(id);
    }
}
