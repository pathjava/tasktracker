package ru.progwards.tasktracker.controller.exceptions;

public class NullObjectException extends RuntimeException {

    public NullObjectException(String message) {
        super(message);
    }
}
