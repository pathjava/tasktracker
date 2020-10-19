package ru.progwards.tasktracker.controller.exceptions;

public class IdNullException extends RuntimeException {
    public IdNullException(String message) {
        super(message);
    }
}
