package ru.progwards.tasktracker.controller.exceptions;

public class NotFoundProjectException extends RuntimeException {

    public NotFoundProjectException(String message) {
        super(message);
    }
}
